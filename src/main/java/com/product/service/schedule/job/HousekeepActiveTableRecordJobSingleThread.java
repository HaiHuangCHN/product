//package com.product.service.schedule.job;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import javax.persistence.EntityManager;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.convention.MatchingStrategies;
//import org.quartz.DisallowConcurrentExecution;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.quartz.PersistJobDataAfterExecution;
//import org.modelmapper.config.Configuration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Isolation;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.product.config.ScheduleJobConfig;
//import com.product.costant.TableNameEnum;
//import com.product.entity.HousekeepSummary;
//import com.product.entity.User;
//import com.product.entity.UserArch;
//import com.product.repository.UserArchRepository;
//import com.product.repository.UserRepository;
//import com.product.util.OtherUtils;
//import com.product.util.TimeUtils;
//
//@DisallowConcurrentExecution
//@PersistJobDataAfterExecution
//public class HousekeepActiveTableRecordJobSingleThread implements Job {
//
//    private static final Logger logger = LogManager.getLogger(HousekeepActiveTableRecordJobSingleThread.class);
//
//    private long userTotal = 0L, cartTotal = 0L, orderDetailTotal = 0L, productItemTotal = 0L;
//
//    private long userArchTotal = 0L, cartArchTotal = 0L, orderDetailArchTotal = 0L, productItemArchTotal = 0L;
//
//    private long totalDeleted = 0L;
//
//    @Autowired
//    private ScheduleJobConfig scheduleJobConfig;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserArchRepository userArchRepository;
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
//    public void archiveData(LocalDateTime now, Map<String, StringBuilder> resultMap) throws Exception {
//        long count = 0L;
//        List<User> userListToBeDeleted = null;
//        List<UserArch> userArchListInserted = new LinkedList<>();
//        UserArch userArch = null;
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STANDARD);
//        LocalDateTime updatedAt = now.minusHours(scheduleJobConfig.getActiveTableRecordRetentionPeriod());
//        userListToBeDeleted = userRepository.findTop10000ByUpdatedAtBefore(updatedAt);
//
//        // step 1: archive records into archive tables
//        for (User user : userListToBeDeleted) {
//            userArch = modelMapper.map(user, UserArch.class);
//            userArchRepository.save(userArch);
////            TODO not work, but why?
////            entityManager.merge(userArch);
////            entityManager.persist(userArch);
//            entityManager.flush();
//            userArchListInserted.add(userArch);
//            count++;
//            if (count % 100 == 0) {
//                entityManager.clear();
//            }
//            if (count == 1L) {
//                resultMap.put("First userId been processed", new StringBuilder().append(user.getUserId()));
//            } else {
//                resultMap.put("Last userId been processed", new StringBuilder().append(user.getUserId()));
//            }
//        }
//
//        if (count == 1L) {
//            resultMap.put("Last bookingId been processed", resultMap.get("First bookingId been processed"));
//        }
//
//        // step 2: delete records from active tables
//        userRepository.deleteAll(userListToBeDeleted);
//        entityManager.flush();
//
//        // step 3: verify archived VS inserted
//        verifyResult(userListToBeDeleted, userArchListInserted);
//
//        resultMap.put("userTotal", new StringBuilder(String.valueOf(userTotal)));
//        resultMap.put("cartTotal", new StringBuilder(String.valueOf(cartTotal)));
//        resultMap.put("orderDetailTotal", new StringBuilder(String.valueOf(orderDetailTotal)));
//        resultMap.put("productItemTotal", new StringBuilder(String.valueOf(productItemTotal)));
//        resultMap.put("userArchTotal", new StringBuilder(String.valueOf(userArchTotal)));
//        resultMap.put("cartArchTotal", new StringBuilder(String.valueOf(cartArchTotal)));
//        resultMap.put("orderDetailArchTotal", new StringBuilder(String.valueOf(orderDetailArchTotal)));
//        resultMap.put("productItemArchTotal", new StringBuilder(String.valueOf(productItemArchTotal)));
//
//        // simulate roll back
////      throw new ErrorResponseException("test", "test", "test");
//    }
//
//    /**
//     * Verify if records deleted are successfully archived
//     * 
//     * @param userListDeleted
//     * @param userArchListInserted
//     * @throws Exception
//     */
//    private void verifyResult(List<User> userListDeleted, List<UserArch> userArchListInserted) throws Exception {
//
//        userTotal += userListDeleted.size();
//        cartTotal += userListDeleted.size();
//        for (User user : userListDeleted) {
//            orderDetailTotal += user.getOrderDetailList().size();
//            productItemTotal += user.getCart().getProductItemList().size();
//        }
//
//        userArchTotal += userArchListInserted.size();
//        cartArchTotal += userArchListInserted.size();
//        for (UserArch userArch : userArchListInserted) {
//            orderDetailArchTotal += userArch.getOrderDetailList().size();
//            productItemArchTotal += userArch.getCart().getProductItemList().size();
//        }
//
//        if (userTotal == userArchTotal && cartTotal == cartArchTotal && orderDetailTotal == orderDetailArchTotal && productItemTotal == productItemArchTotal) {
//            totalDeleted = userTotal + cartTotal + orderDetailTotal + productItemTotal;
//        } else {
//            throw new Exception("Deleted total not match archived total!");
//        }
//
//    }
//
//    public void executeArchiveDatabaseRecordJob() {
//        LocalDateTime now = TimeUtils.getUtcTime();
//        Map<String, StringBuilder> resultMap = new HashMap<String, StringBuilder>();
//        List<HousekeepSummary> housekeepSummaryList = new LinkedList<>();
//
//        if (scheduleJobConfig.getEnableArchiveActiveTableRecordJob()) {
//            try {
//                archiveData(now, resultMap);
//                // step 4: persist housekeep result when success
//                housekeepSummaryList.add(OtherUtils.genHousekeepResult(TableNameEnum.USER.getArchiveTableName(), TableNameEnum.USER.getActiveTableName(),
//                        Long.valueOf(resultMap.get("userTotal").toString()), HousekeepSummary.StatusEnum.SUCCESS, "", now));
//                housekeepSummaryList.add(OtherUtils.genHousekeepResult(TableNameEnum.CART.getArchiveTableName(), TableNameEnum.CART.getActiveTableName(),
//                        Long.valueOf(resultMap.get("cartTotal").toString()), HousekeepSummary.StatusEnum.SUCCESS, "", now));
//                housekeepSummaryList.add(OtherUtils.genHousekeepResult(TableNameEnum.ORDER_DETAIL.getArchiveTableName(), TableNameEnum.ORDER_DETAIL.getActiveTableName(),
//                        Long.valueOf(resultMap.get("orderDetailTotal").toString()), HousekeepSummary.StatusEnum.SUCCESS, "", now));
//                housekeepSummaryList.add(OtherUtils.genHousekeepResult(TableNameEnum.PRODUCT_ITEM.getArchiveTableName(), TableNameEnum.PRODUCT_ITEM.getActiveTableName(),
//                        Long.valueOf(resultMap.get("productItemTotal").toString()), HousekeepSummary.StatusEnum.SUCCESS, "", now));
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error(e.toString());
//                String statuMsg = e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage();
//                housekeepSummaryList
//                        .add(OtherUtils.genHousekeepResult(TableNameEnum.USER.getArchiveTableName(), TableNameEnum.USER.getActiveTableName(), 0L, HousekeepSummary.StatusEnum.FAIL, statuMsg, now));
//                housekeepSummaryList
//                        .add(OtherUtils.genHousekeepResult(TableNameEnum.CART.getArchiveTableName(), TableNameEnum.CART.getActiveTableName(), 0L, HousekeepSummary.StatusEnum.FAIL, statuMsg, now));
//                housekeepSummaryList.add(OtherUtils.genHousekeepResult(TableNameEnum.ORDER_DETAIL.getArchiveTableName(), TableNameEnum.ORDER_DETAIL.getActiveTableName(), 0L,
//                        HousekeepSummary.StatusEnum.FAIL, statuMsg, now));
//                housekeepSummaryList.add(OtherUtils.genHousekeepResult(TableNameEnum.PRODUCT_ITEM.getArchiveTableName(), TableNameEnum.PRODUCT_ITEM.getActiveTableName(), 0L,
//                        HousekeepSummary.StatusEnum.FAIL, statuMsg, now));
//            } finally {
//                logger.info("First userId been processed: " + resultMap.get("First userId been processed"));
//                logger.info("Last userId been processed: " + resultMap.get("Last userId been processed"));
//                logger.info("Housekeep detail of active tables: user = {}, cart = {}, orderDetail = {}, product_item = {}", resultMap.get("userTotal"), resultMap.get("cartTotal"),
//                        resultMap.get("orderDetailTotal"), resultMap.get("productItemTotal"));
//                logger.info("Housekeep detail of archive tables: user_arch = {}, cart_arch = {}, order_detail_arch = {}, product_item_arch = {}", resultMap.get("userArchTotal"),
//                        resultMap.get("cartArchTotal"), resultMap.get("orderDetailArchTotal"), resultMap.get("productItemArchTotal"));
//                housekeepSummaryRepository.saveAll(housekeepSummaryList);
//            }
//        }
//    }
//
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        // TODO Auto-generated method stub
//
//    }
//}
