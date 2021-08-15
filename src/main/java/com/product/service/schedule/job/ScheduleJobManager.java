package com.product.service.schedule.job;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO optimize Quartz to follow the best practice
 */
@Service
public class ScheduleJobManager {

    private static final Logger logger = LogManager.getLogger(ScheduleJobManager.class);

    /* Inject job Scheduler （注入调度器） */
    @Autowired
    private Scheduler scheduler;

    /**
     * Schedule a job
     * 
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @param jobDescription
     * @param cron
     * @param clazz
     * @param methodName
     * @throws SchedulerException
     */
    public void scheduleJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String jobDescription, String cron, Class<?> clazz, String methodName, Class<? extends Job> jobClazz)
            throws SchedulerException {
        if (scheduler.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName)) == null) {
            Date startTime = new Date(System.currentTimeMillis() + 60000);
            // We customize parameters with a jobDataMap instance that will be used in job
            // instance
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("jobGroupName", jobGroupName);
            jobDataMap.put("jobName", jobName);
            jobDataMap.put("triggerGroupName", triggerGroupName);
            jobDataMap.put("triggerName", triggerName);
            if (clazz != null) {
                jobDataMap.put("classPath", clazz.getName());
                jobDataMap.put("methodName", methodName);
            }
            // Using JobBuilder to build jobDetail which is the instance of the Job class
            JobDetail jobDetail = JobBuilder.newJob(jobClazz).withDescription(jobDescription).withIdentity(jobName, jobGroupName).usingJobData(jobDataMap).requestRecovery(true).build();
            // Build CronTrigger based on cron expression
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionIgnoreMisfires();
            // CronTrigger is implementation of Trigger
            // Use TriggerBuilder to construct a new instance of trigger（触发器） by
            // initializing type with cronScheduleBuilder
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(cronScheduleBuilder).startAt(startTime).build();
//            // Way 1: Global configuration for Job Listener
//            scheduler.getListenerManager().addJobListener(new ProductScheduleJobListener(), EverythingMatcher.allJobs());
//            // Way 2: Local configuration for Job Listener
//            scheduler.getListenerManager().addJobListener(new ScheduleJobListener(), KeyMatcher.keyEquals(new JobKey("job1", "jobGroup1")));
            scheduler.scheduleJob(jobDetail, cronTrigger);
        }
    }

    /**
     * Re-schedule a job by providing a different cron expression
     * 
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     */
    public void rescheduleJob(String triggerName, String triggerGroupName, String cron) {
        try {
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
            CronTrigger oldCronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (oldCronTrigger == null) {
                throw new SchedulerException("No such trigger");
            }
            String oldTime = oldCronTrigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
                CronTrigger newCrontrigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(cronScheduleBuilder).build();
                scheduler.rescheduleJob(triggerKey, newCrontrigger);
            } else {
                logger.info("Same cron of trigger name: {}, trigger group name: {}", triggerName, triggerGroupName);
            }
        } catch (Exception e) {
            logger.error("Exception:{}", e);
        }

    }

    public void scheduleDelete(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (jobDetail == null || trigger == null) {
                throw new SchedulerException("No such job");                
            }
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            logger.error("Exception:{}", e);
        }
    }

    public void scheduleDeleteAll() {
        try {
            List<String> jobGroupNamesList = scheduler.getJobGroupNames();
            for (String jobGroupName : jobGroupNamesList) {
                GroupMatcher<JobKey> jobKeyGroupMatcher = GroupMatcher.jobGroupEquals(jobGroupName);
                Set<JobKey> jobKeySet = scheduler.getJobKeys(jobKeyGroupMatcher);
                for (JobKey jobKey : jobKeySet) {
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    if (jobDetail == null) {
                        throw new SchedulerException("No such job");
                    }
                    scheduler.deleteJob(jobKey);
                    logger.info("job group name: {}, job name: {}", jobKey.getGroup(), jobKey.getName());
                }
            }
        } catch (Exception e) {
            logger.error("Exception:{}", e);
        }
    }

    /**
     * Pause job
     * 
     * @param jobName
     * @param jobGroupName
     */
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                throw new SchedulerException("No such job");
            }
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            logger.error("Exception:{}", e);
        }
    }

    public void resumeJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = new JobKey(jobName, jobGroupName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                return;
            }
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            logger.error("Exception:{}", e);
        }
    }

    public void startScheduler() throws SchedulerException {
        scheduler.start();
    }

    public void standByScheduler() throws SchedulerException {
        scheduler.standby();
    }

    public void shutDownScheduler() throws SchedulerException {
        scheduler.shutdown();
    }

}