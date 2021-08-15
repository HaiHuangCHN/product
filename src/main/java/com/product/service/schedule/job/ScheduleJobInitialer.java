package com.product.service.schedule.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import com.product.config.ScheduleJobConfig;
import com.product.costant.ScheduleJobEnum;

/**
 * Must add @Service to let the class as a bean source, so that run() method
 * will take effect
 */
@Service
public class ScheduleJobInitialer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobInitialer.class);

    @Autowired
    private ScheduleJobManager scheduleJobManager;

    @Autowired
    private ScheduleJobConfig scheduleJobConfig;

    /**
     * For each schedule job, there are 2 steps when initialize:<br>
     * 1. Try to create a new schedule job if the job / trigger not exists in DB<br>
     * 2. If the schedule job already exists in DB, try to update with the latest
     * cron setup and reschedule if schedule time is different from existing cron
     * setup
     * 
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        try {
            scheduleJobManager.scheduleDeleteAll();
            // Schedule job 1
//            scheduleJobManager.scheduleJob(ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getJobName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getJobGroupName(),
//                    ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getTriggerName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getTriggerGroupName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getDescription(),
//                    scheduleJobConfig.getScheduleJobOneCron(), ScheduleJob.class, "executeScheduleJobOne", ScheduleJob.class);
//            scheduleJobManager.rescheduleJob(ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getTriggerName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getTriggerGroupName(),
//                    scheduleJobConfig.getScheduleJobOneCron());
//
//            // Schedule job 2
//            scheduleJobManager.scheduleJob(ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getJobName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getJobGroupName(),
//                    ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getTriggerName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getTriggerGroupName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getDescription(),
//                    scheduleJobConfig.getScheduleJobTwoCron(), ScheduleJob.class, "executeScheduleJobTwo", ScheduleJob.class);
//            scheduleJobManager.rescheduleJob(ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getTriggerName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getTriggerGroupName(),
//                    scheduleJobConfig.getScheduleJobTwoCron());
//
//            // Schedule job 2
//            scheduleJobManager.scheduleJob(ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getJobName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getJobGroupName(),
//                    ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getTriggerName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getTriggerGroupName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getDescription(),
//                    scheduleJobConfig.getScheduleJobThreeCron(), ScheduleJob.class, "executeScheduleJobThree", ScheduleJob.class);
//            scheduleJobManager.rescheduleJob(ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getTriggerName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getTriggerGroupName(),
//                    scheduleJobConfig.getScheduleJobThreeCron());
//
//            // Delete housekeep_summary record Job
//            scheduleJobManager.scheduleJob(ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getJobName(), ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getJobGroupName(),
//                    ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getTriggerName(), ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getTriggerGroupName(),
//                    ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getDescription(), scheduleJobConfig.getDeleteHousekeepSummaryRecordJobCron(), null, null,
//                    DeleteHousekeepSummaryRecordJob.class);
//            scheduleJobManager.rescheduleJob(ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getTriggerName(), ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getTriggerGroupName(),
//                    scheduleJobConfig.getDeleteHousekeepSummaryRecordJobCron());
//
//            // Archive Database Record Job
//            scheduleJobManager.scheduleJob(ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getJobName(), ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getJobGroupName(),
//                    ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getTriggerName(), ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getTriggerGroupName(),
//                    ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getDescription(), scheduleJobConfig.getArchiveActiveTableRecordJobCron(), null, null, HousekeepActiveTableRecordJobSingleThread.class);
//            scheduleJobManager.rescheduleJob(ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getTriggerName(), ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getTriggerGroupName(),
//                    scheduleJobConfig.getArchiveActiveTableRecordJobCron());
//            scheduleJobManager.standByScheduler();
//            scheduleJobManager.shutDownScheduler();
//            scheduleJobManager.startScheduler();
        } catch (Exception e) {
            logger.error("Schedule job fails to initialize due to exception", e);
        }
    }
}
