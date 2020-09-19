package com.product.service.schedule.job;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.product.config.ScheduleJobConfig;
import com.product.costant.TableNameEnum;
import com.product.entity.HousekeepSummary;
import com.product.repository.HousekeepSummaryRepository;
import com.product.util.OtherUtils;
import com.product.util.TimeUtils;

@Service
// Same jobDetail can NOT concurrent execute，different jobDetail can concurrent execute
@DisallowConcurrentExecution
// TODO 使用场景是什么？
// This is to persist updated jobDataMap detail into DB,
//Add 将该注解加在job类上，告诉Quartz在成功执行了job类的execute方法后（没有发生任何异常），更新JobDetail中JobDataMap的数据，使得该job（即JobDetail）在下一次执行的时候，JobDataMap中是更新后的数据，而不是更新前的旧数据
@PersistJobDataAfterExecution
public class ScheduleJob implements Job {

    private static final Logger logger = LogManager.getLogger(ScheduleJob.class);

    @Autowired
    private ScheduleJobConfig scheduleJobConfig;

    @Autowired
    private HousekeepActiveTableRecordJobSingleThread housekeepJob;

    @Autowired
    private HousekeepSummaryRepository housekeepSummaryRepository;

//    /**
//     * Business logic of schedule job<br>
//     * As long as scheduler triggers the job, the method will execute as the
//     * following implementation
//     * 
//     * @author Huang, Hai
//     * @date Feb 6, 2020 6:22:10 PM
//     * @email hai.huang.a@outlook.com
//     *
//     * @param jobExecutionContext Job execution context
//     * @throws JobExecutionException
//     */
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        String group = jobExecutionContext.getJobDetail().getJobDataMap().get("group").toString();
//        String name = jobExecutionContext.getJobDetail().getJobDataMap().get("name").toString();
//        String classPath = jobExecutionContext.getJobDetail().getJobDataMap().get("classPath").toString();
//        String method = jobExecutionContext.getJobDetail().getJobDataMap().get("method").toString();
//        logger.info("Execute task! group: {}, name: {}, class path: {}, method: {}", group, name, classPath, method);
//        // Execute scheduled concrete/specific business logic
//        // ...
//    }

    /**
     * One of the business usages can be like the following
     */
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        try {
            JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            String jobGroupName = jobDataMap.get("jobGroupName").toString();
            String jobName = jobDataMap.get("jobName").toString();
            Class<?> clazz = Class.forName(jobDataMap.getString("classPath"));
            if (clazz != null) {
                Object obj = applicationContext.getBean(clazz);
                Method method = clazz.getDeclaredMethod(jobDataMap.getString("methodName"));
                if (method != null && obj != null) {
                    logger.info("Execute task! job group name: {}, job name: {}", jobGroupName, jobName);
                    method.invoke(obj);
                }
            }
        } catch (Exception e) {
            logger.error("Method invoke failed, due to exception", e);
        }

    }

    public void executeScheduleJobOne() {
        logger.info("Successfully execute schedule job 1!");
    }

    public void executeScheduleJobTwo() {
        logger.info("Successfully execute schedule job 2!");
    }

    public void executeScheduleJobThree() {
        logger.info("Successfully execute schedule job 3!");
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void executeDeleteHousekeepSummaryRecordJob() {
        if (scheduleJobConfig.getEnableDeleteHousekeepSummaryRecordJob()) {
            LocalDateTime updatedAt = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().minusSeconds(scheduleJobConfig.getHousekeepSummaryRecordRetentionPeriod());
            List<HousekeepSummary> deletedList = housekeepSummaryRepository.deleteByUpdatedAtBefore(updatedAt);
            // simulate roll back
            // throw new ErrorResponseException("test", "test", "test");
            if (!deletedList.isEmpty()) {
                logger.info("Successfully housekeep {} records that is older than {}. From id {} to {}.", deletedList.size(), updatedAt, deletedList.get(0).getHousekeepSummaryId(),
                        deletedList.get(deletedList.size() - 1).getHousekeepSummaryId());
            }
        }
    }

    public void executeArchiveDatabaseRecordJob() {
        LocalDateTime now = TimeUtils.getUtcTime();
        Map<String, StringBuilder> resultMap = new HashMap<String, StringBuilder>();
        List<HousekeepSummary> housekeepSummaryList = new LinkedList<>();

        if (scheduleJobConfig.getEnableArchiveActiveTableRecordJob()) {
            try {
                housekeepJob.archiveData(now, resultMap);
                // step 4: persist housekeep result when success
                housekeepSummaryList.add(OtherUtils.genHousekeepResult(TableNameEnum.USER.getArchiveTableName(), TableNameEnum.USER.getActiveTableName(),
                        Long.valueOf(resultMap.get("userTotal").toString()), HousekeepSummary.StatusEnum.SUCCESS, "", now));
                housekeepSummaryList.add(OtherUtils.genHousekeepResult(TableNameEnum.CART.getArchiveTableName(), TableNameEnum.CART.getActiveTableName(),
                        Long.valueOf(resultMap.get("cartTotal").toString()), HousekeepSummary.StatusEnum.SUCCESS, "", now));
                housekeepSummaryList.add(OtherUtils.genHousekeepResult(TableNameEnum.ORDER_DETAIL.getArchiveTableName(), TableNameEnum.ORDER_DETAIL.getActiveTableName(),
                        Long.valueOf(resultMap.get("orderDetailTotal").toString()), HousekeepSummary.StatusEnum.SUCCESS, "", now));
                housekeepSummaryList.add(OtherUtils.genHousekeepResult(TableNameEnum.PRODUCT_ITEM.getArchiveTableName(), TableNameEnum.PRODUCT_ITEM.getActiveTableName(),
                        Long.valueOf(resultMap.get("productItemTotal").toString()), HousekeepSummary.StatusEnum.SUCCESS, "", now));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.toString());
                String statuMsg = e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage();
                housekeepSummaryList
                        .add(OtherUtils.genHousekeepResult(TableNameEnum.USER.getArchiveTableName(), TableNameEnum.USER.getActiveTableName(), 0L, HousekeepSummary.StatusEnum.FAIL, statuMsg, now));
                housekeepSummaryList
                        .add(OtherUtils.genHousekeepResult(TableNameEnum.CART.getArchiveTableName(), TableNameEnum.CART.getActiveTableName(), 0L, HousekeepSummary.StatusEnum.FAIL, statuMsg, now));
                housekeepSummaryList.add(OtherUtils.genHousekeepResult(TableNameEnum.ORDER_DETAIL.getArchiveTableName(), TableNameEnum.ORDER_DETAIL.getActiveTableName(), 0L,
                        HousekeepSummary.StatusEnum.FAIL, statuMsg, now));
                housekeepSummaryList.add(OtherUtils.genHousekeepResult(TableNameEnum.PRODUCT_ITEM.getArchiveTableName(), TableNameEnum.PRODUCT_ITEM.getActiveTableName(), 0L,
                        HousekeepSummary.StatusEnum.FAIL, statuMsg, now));
            } finally {
                logger.info("First userId been processed: " + resultMap.get("First userId been processed"));
                logger.info("Last userId been processed: " + resultMap.get("Last userId been processed"));
                logger.info("Housekeep detail of active tables: user = {}, cart = {}, orderDetail = {}, product_item = {}", resultMap.get("userTotal"), resultMap.get("cartTotal"),
                        resultMap.get("orderDetailTotal"), resultMap.get("productItemTotal"));
                logger.info("Housekeep detail of archive tables: user_arch = {}, cart_arch = {}, order_detail_arch = {}, product_item_arch = {}", resultMap.get("userArchTotal"),
                        resultMap.get("cartArchTotal"), resultMap.get("orderDetailArchTotal"), resultMap.get("productItemArchTotal"));
                housekeepSummaryRepository.saveAll(housekeepSummaryList);
            }
        }
    }

}