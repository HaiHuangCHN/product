//package com.product.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.product.config.HousekeepConfig;
//import com.product.data.DataProvider;
//
//@Service
//public class QuartzJobServiceImpl implements QuartzJobService {
//
//    private static final Logger logger = LoggerFactory.getLogger(QuartzJobServiceImpl.class);
//
//    @Autowired
//    HousekeepConfig housekeepConfig;
//
//    @Autowired
//    private DataProvider dataProvider;
//
//    public void executeHousekeepSummaryTableJob() throws Exception {
//        if (housekeepConfig.getEnableHousekeepSummaryTableJob()) {
//            dataProvider.deleteByUpdatedAtBeforeNSecond(housekeepConfig.getSummaryTableRetentionPeriod());
//        }
//        logger.info("Successfully execute Housekeep Summary Table Job!");
//    }
//
//}
