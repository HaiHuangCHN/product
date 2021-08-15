package com.product.service.schedule.job;

import java.lang.reflect.Method;

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

@Service
// Same jobDetail can NOT concurrent execute，different jobDetail can concurrent execute
@DisallowConcurrentExecution
// TODO 使用场景是什么？
// This is to persist updated jobDataMap detail into DB,
//Add 将该注解加在job类上，告诉Quartz在成功执行了job类的execute方法后（没有发生任何异常），更新JobDetail中JobDataMap的数据，使得该job（即JobDetail）在下一次执行的时候，JobDataMap中是更新后的数据，而不是更新前的旧数据
@PersistJobDataAfterExecution
public class ScheduleJob implements Job {

    private static final Logger logger = LogManager.getLogger(ScheduleJob.class);

//    /**
//     * Business logic of schedule job<br>
//     * As long as scheduler triggers the job, the method will execute as the
//     * following implementation
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
                if (method != null) {
                    logger.info("Execute task! job group name: {}, job name: {}", jobGroupName, jobName);
                    method.invoke(obj);
                }
            }
        } catch (Exception e) {
            logger.error("Method invoke failed, due to exception", e);
        }

    }

    public void executeScheduleJobOne() {
        logger.info("Executed schedule job 1!");
    }

    public void executeScheduleJobTwo() {
        logger.info("Executed schedule job 2!");
    }

    public void executeScheduleJobThree() {
        logger.info("Executed schedule job 3!");
    }


}