package com.product.service.schedule.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Job listener (监听器)<br>
 * 1. it won't support persistence since 2.x<br>
 * 2. it not supports distributed system<br>
 * 3. it only support to be registered when boot application
 */
public class ScheduleJobListener implements JobListener {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobListener.class);

    @Override
    public String getName() {
        return "Test job listener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        logger.info("ProductScheduleJobListener.jobToBeExecuted()");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        logger.info("ProductScheduleJobListener.jobExecutionVetoed()");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        logger.info("ProductScheduleJobListener.jobWasExecuted()");
    }
}
