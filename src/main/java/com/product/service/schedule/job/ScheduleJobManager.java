package com.product.service.schedule.job;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
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
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.model.commom.ScheduleJobModel;

/**
 * TODO optimize Quartz to follow the best practice
 */
@Service
public class ScheduleJobManager {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobManager.class);

    /* Inject job Scheduler （注入调度器） */
    @Autowired
    private Scheduler scheduler;

    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String jobDescription, String cron, Class<?> clazz, String methodName)
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
            jobDataMap.put("classPath", clazz.getName());
            jobDataMap.put("methodName", methodName);
            // Using JobBuilder to build jobDetail which is the instance of the Job class
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withDescription(jobDescription).withIdentity(jobName, jobGroupName).usingJobData(jobDataMap)
                    .requestRecovery(true).build();
            // Build CronTrigger based on cron expression
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionIgnoreMisfires();
            // CronTrigger is implementation of Trigger
            // Use TriggerBuilder to construct a new instance of trigger（触发器） by
            // initializing type with cronScheduleBuilder
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(cronScheduleBuilder).startAt(startTime).build();
//        // Way 1: global configuration for Job Listener
//        scheduler.getListenerManager().addJobListener(new ProductScheduleJobListener(), EverythingMatcher.allJobs());
            // Way 2: local configuration for Job Listener
            scheduler.getListenerManager().addJobListener(new ScheduleJobListener(), KeyMatcher.keyEquals(new JobKey("job1", "jobGroup1")));
            scheduler.scheduleJob(jobDetail, cronTrigger);
        }
    }

    public void scheduleUpdateCorn(ScheduleJobModel model) {
        try {
            TriggerKey triggerKey = new TriggerKey(model.getTriggerName(), model.getTriggerGroupName());
            CronTrigger oldCronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (oldCronTrigger == null) {
                return;
            }
            String oldTime = oldCronTrigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(model.getCron())) {
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(model.getCron());
                CronTrigger newCrontrigger = TriggerBuilder.newTrigger().withIdentity(model.getTriggerName(), model.getTriggerGroupName()).withSchedule(cronScheduleBuilder)
                        .build();
                scheduler.rescheduleJob(triggerKey, newCrontrigger);
            } else {
                throw new SchedulerException("Same cron expression");
            }
        } catch (Exception e) {
            logger.info("Exception:{}", e);
        }

    }

    public void scheduleUpdateCorn(String triggerName, String triggerGroupName, String cron) {
        try {
            TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroupName);
            CronTrigger oldCronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (oldCronTrigger == null) {
                return;
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
            logger.info("Exception:{}", e);
        }

    }

    public void schedulePause(ScheduleJobModel model) {
        try {
            JobKey jobKey = new JobKey(model.getJobName(), model.getJobGroupName());
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                return;
            }
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            logger.error("Exception:{}", e);
        }
    }

    public void scheduleResume(ScheduleJobModel model) {
        try {
            JobKey jobKey = new JobKey(model.getJobName(), model.getJobGroupName());
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail == null) {
                return;
            }
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            logger.error("Exception:{}", e);
        }
    }

    public void scheduleDelete(ScheduleJobModel model) {
        try {
            JobKey jobKey = new JobKey(model.getJobName(), model.getJobGroupName());
            TriggerKey triggerKey = new TriggerKey(model.getTriggerName(), model.getTriggerGroupName());
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (jobDetail == null || trigger == null)
                return;
            scheduler.deleteJob(jobKey);
//or            scheduler.deleteJob(JobKey.jobKey(model.getJobName(), model.getJobGroupName()););
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
                    if (jobDetail == null)
                        return;
                    scheduler.deleteJob(jobKey);
                    logger.info("job group name: {}, job name: {}", jobKey.getGroup(), jobKey.getName());
                }
            }
        } catch (Exception e) {
            logger.error("Exception:{}", e);
        }
    }

    public void startScheduler() throws SchedulerException {
        scheduler.start();
    }

    public void shutDownScheduler() throws SchedulerException {
        scheduler.shutdown();
    }

    public void standByScheduler() throws SchedulerException {
        scheduler.standby();
    }

}