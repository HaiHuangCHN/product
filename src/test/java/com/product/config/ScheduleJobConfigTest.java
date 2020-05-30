package com.product.config;

import javax.sql.DataSource;

import org.mockito.Mockito;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@TestConfiguration
public class ScheduleJobConfigTest {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource) throws Exception {
        return Mockito.mock(SchedulerFactoryBean.class);
    }

    @Bean
    public Scheduler scheduler() {
        return Mockito.mock(Scheduler.class);
    }

}
