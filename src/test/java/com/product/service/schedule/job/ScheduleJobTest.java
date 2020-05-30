//package com.product.service.schedule.job;
//
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.product.Application;
//import com.product.config.RestTemplateConfigTest;
//import com.product.config.ScheduleJobConfigTest;
//import com.product.data.DataProvider;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = { Application.class })
//@ContextConfiguration(classes = { ScheduleJobConfigTest.class, RestTemplateConfigTest.class })
//public class ScheduleJobTest {
//
//    @Autowired
//    DataProvider dataProvider;
//
//    public void executeDeleteHousekeepSummaryRecordJob() throws Exception {
//        dataProvider.deleteByUpdatedAtBeforeNSecond();
//    }
//    
//    
//    @RunWith(SpringRunner.class)
//    @SpringBootTest(classes = Application.class)
//    @ContextConfiguration(classes = UnitTestConfig.class)
//    
//        @Autowired
//        HousekeepJobConfig housekeepJobConfig;
//    
//        @Autowired
//        QuartzJobService quartzJobService;
//    
//        @Autowired
//        HousekeepSummaryRepository housekeepSummaryRepository;
//    
//        @Before
//        public void setupData() {
//            LocalDateTime updatedAt = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
//            List<HousekeepSummary> housekeepSummaryList = new LinkedList<HousekeepSummary>();
//            for (int i = 1499; i >= 0; i--) {
//                HousekeepSummary housekeepSummary = new HousekeepSummary();
//                housekeepSummary.setCreatedAt(updatedAt.minusDays(i));
//                housekeepSummary.setUpdatedAt(updatedAt.minusDays(i));
//                housekeepSummaryList.add(housekeepSummary);
//            }
//            housekeepSummaryRepository.save(housekeepSummaryList);
//        }
//    
//        @Test
//        public void testExecuteHousekeepSummaryTableJobEnable() {
//            LocalDateTime dateExecuteHousekeep = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
//    
//            housekeepJobConfig.setEnableHousekeepSummaryTableJob(true);
//    
//            quartzJobService.executeHousekeepSummaryTableJob();
//    
//            LocalDate now = dateExecuteHousekeep.toLocalDate();
//            LocalDate past = dateExecuteHousekeep.toLocalDate().minusMonths(housekeepJobConfig.getSummaryTableRetentionPeriod());
//            long offset = now.toEpochDay() - past.toEpochDay();
//            assertTrue(housekeepSummaryRepository.findAll().size() == offset);
//        }
//    
//        @Test
//        public void testExecuteHousekeepSummaryTableJobDisable() {
//            housekeepJobConfig.setEnableHousekeepSummaryTableJob(false);
//    
//            quartzJobService.executeHousekeepSummaryTableJob();
//    
//            assertTrue(housekeepSummaryRepository.findAll().size() == 1500);
//        }
//    
//        @After
//        public void cleanData() {
//            housekeepSummaryRepository.deleteAll();
//        }
//    
//
//
//}