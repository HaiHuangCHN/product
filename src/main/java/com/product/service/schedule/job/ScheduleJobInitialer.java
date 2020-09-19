package com.product.service.schedule.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.stereotype.Service;

import com.product.config.ScheduleJobConfig;
import com.product.costant.ScheduleJobEnum;

@Service
public class ScheduleJobInitialer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleJob.class);

    @Autowired
    private ScheduleJobManager scheduleJobManager;

    @Autowired
    private ScheduleJobConfig scheduleJobConfig;

    /**
     * For each schedule job, there are 2 steps when initialize:<br>
     * 1. try to create a new schedule job if the job / trigger not exists in DB<br>
     * 2. if the scheudle job already exists in DB, try to update with the latest
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
            // schedule job 1
            scheduleJobManager.addJob(ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getJobName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getJobGroupName(),
                    ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getTriggerName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getTriggerGroupName(),
                    ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getDescription(), scheduleJobConfig.getScheduleJobOneCron(), ScheduleJob.class, "executeScheduleJobOne");
            scheduleJobManager.scheduleUpdateCorn(ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getTriggerName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_1.getTriggerGroupName(),
                    scheduleJobConfig.getScheduleJobOneCron());

            // schedule job 2
            scheduleJobManager.addJob(ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getJobName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getJobGroupName(),
                    ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getTriggerName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getTriggerGroupName(),
                    productScheduleJobConfig.getScheduleJobTwoCron(), ProductScheduleJob.class, "executeScheduleJobTwo");
            scheduleJobManager.scheduleUpdateCorn(ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getTriggerName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_2.getTriggerGroupName(),
                    productScheduleJobConfig.getScheduleJobTwoCron());

            // schedule job 2
            scheduleJobManager.addJob(ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getJobName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getJobGroupName(),
                    ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getTriggerName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getTriggerGroupName(),
                    productScheduleJobConfig.getScheduleJobThreeCron(), ProductScheduleJob.class, "executeScheduleJobThree");
            scheduleJobManager.scheduleUpdateCorn(ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getTriggerName(), ScheduleJobEnum.PRODUCT_SCHEDULE_JOB_3.getTriggerGroupName(),
                    productScheduleJobConfig.getScheduleJobThreeCron());

            // Delete housekeep_summary record Job
            scheduleJobManager.addJob(ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getJobName(), ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getJobGroupName(),
                    ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getTriggerName(), ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getTriggerGroupName(),
                    scheduleJobConfig.getDeleteHousekeepSummaryRecordJobCron(), ScheduleJob.class, "executeDeleteHousekeepSummaryRecordJob");
            scheduleJobManager.scheduleUpdateCorn(ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getTriggerName(),
                    ScheduleJobEnum.DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB.getTriggerGroupName(), scheduleJobConfig.getDeleteHousekeepSummaryRecordJobCron());

            // Archive Database Record Job
            scheduleJobManager.addJob(ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getJobName(), ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getJobGroupName(),
                    ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getTriggerName(), ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getTriggerGroupName(),
                    scheduleJobConfig.getArchiveActiveTableRecordJobCron(), ScheduleJob.class, "executeArchiveDatabaseRecordJob");
            scheduleJobManager.scheduleUpdateCorn(ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getTriggerName(), ScheduleJobEnum.ARCHIVE_DATABASE_RECORD_JOB.getTriggerGroupName(),
                    scheduleJobConfig.getArchiveActiveTableRecordJobCron());
            scheduleJobManager.standByScheduler();
            scheduleJobManager.shutDownScheduler();
            scheduleJobManager.startScheduler();
        } catch (Exception e) {
            logger.error("Schedule job fails to execute due to exception", e);
        }
    }
}
