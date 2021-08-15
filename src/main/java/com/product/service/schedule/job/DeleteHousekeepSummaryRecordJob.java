package com.product.service.schedule.job;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.product.config.ScheduleJobConfig;
import com.product.entity.HousekeepSummary;
import com.product.repository.HousekeepSummaryRepository;

/**
 * TODO Can it roll back?
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class DeleteHousekeepSummaryRecordJob implements Job {

    private static final Logger logger = LogManager.getLogger(DeleteHousekeepSummaryRecordJob.class);

    @Autowired
    private ScheduleJobConfig scheduleJobConfig;

    @Autowired
    private HousekeepSummaryRepository housekeepSummaryRepository;

    /**
     * Business logic of schedule job<br>
     * As long as scheduler triggers the job, the method will execute as the
     * following implementation
     * 
     * @param jobExecutionContext Job execution context
     * @throws JobExecutionException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (scheduleJobConfig.getEnableDeleteHousekeepSummaryRecordJob()) {
            logger.info("Job ** {} ** fired @ {}", jobExecutionContext.getJobDetail().getKey().getName(), jobExecutionContext.getFireTime());
            LocalDateTime updatedAt = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime().minusSeconds(scheduleJobConfig.getHousekeepSummaryRecordRetentionPeriod());
            List<HousekeepSummary> deletedList = housekeepSummaryRepository.deleteByUpdatedAtBefore(updatedAt);
            // simulate roll back
//            int i = 4 / 0;
            if (!deletedList.isEmpty()) {
                logger.info("Successfully housekeep {} records that is older than {}. From id {} to {}.", deletedList.size(), updatedAt, deletedList.get(0).getHousekeepSummaryId(),
                        deletedList.get(deletedList.size() - 1).getHousekeepSummaryId());
            }
        }

        logger.info("Next job executed @ {}", jobExecutionContext.getNextFireTime());
    }

}