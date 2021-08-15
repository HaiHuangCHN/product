package com.product.config;

import java.util.Properties;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.validation.annotation.Validated;

import com.product.costant.Constants;
import com.product.util.RetentionPeriodValidation;

@Validated
@Configuration
@ConfigurationProperties(prefix = "job")
@DependsOn("systemConfig")
public class ScheduleJobConfig {

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @NotNull
    private Boolean enableScheduleJobOne;

    @NotNull
    private String scheduleJobOneCron;

    @NotNull
    private Boolean enableScheduleJobTwo;

    @NotNull
    private String scheduleJobTwoCron;

    @NotNull
    private Boolean enableScheduleJobThree;

    @NotNull
    private String scheduleJobThreeCron;

    @NotNull
    private Boolean enableDeleteHousekeepSummaryRecordJob;

    @RetentionPeriodValidation
    private Integer housekeepSummaryRecordRetentionPeriod;

    @NotNull
    private String deleteHousekeepSummaryRecordJobCron;

    @NotNull
    private Boolean enableArchiveActiveTableRecordJob;

    @RetentionPeriodValidation
    private Integer activeTableRecordRetentionPeriod;

    @NotNull
    private String archiveActiveTableRecordJobCron;

    /**
     * The SpringBeanJobFactory allows you to inject properties from the scheduler
     * context, job data map and trigger data entries into the job bean. But there
     * is no way out of the box to inject beans from the application context.
     * 
     * So I came up with the factory bean below that extends the
     * SpringBeanJobFactory to add auto-wiring support:<br>
     * (Extends org.springframework.scheduling.quartz.SpringBeanJobFactory to
     * override createJobInstance() to create a spring-managed scheduler Instance)
     * 
     * @see http://www.btmatthews.com/blog/2011/inject-application-context+dependencies-in-quartz-job-beans.html
     */
    public static final class AutowiringAdaptableJobFactory extends AdaptableJobFactory implements ApplicationContextAware {

        private AutowireCapableBeanFactory beanFactory;

        @Override // Implements ApplicationContextAware
        public void setApplicationContext(final ApplicationContext applicatioinContext) {
            beanFactory = applicatioinContext.getAutowireCapableBeanFactory();
        }

        @Override // Extends AdaptableJobFactory
        protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
            final Object jobInstance = super.createJobInstance(bundle);
            beanFactory.autowireBean(jobInstance);
            return jobInstance;
        }
    }

    /**
     * Configure jobFactory instance<br>
     * see {@link AutowiringAdaptableJobFactory}
     * 
     * @param applicationContext Spring Application Context
     * @return customized jobFactory bean
     */
    @Bean
    public JobFactory springBeanJobFactory(ApplicationContext applicationContext) {
        AutowiringAdaptableJobFactory jobFactory = new AutowiringAdaptableJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    /**
     * Configure scheduler
     * 
     * TODO Could we set up a dedicated DB for Quartz, configure it like this:
     * #org.quartz.dataSource.default.driver=com.mysql.jdbc.Driver
     * #org.quartz.dataSource.default.URL=jdbc:mysql://localhost:3306/product?serverTimezone=UTC&characterEncoding=utf8&autoReconnect=true&useSSL=false
     * #org.quartz.dataSource.default.user=hai
     * #org.quartz.dataSource.default.password=199510
     * #org.quartz.dataSource.default.maxConnections=5
     *
     * @param jobFactory customized jobFactory
     * @param dataSource data source instance
     * @return
     */
    @Bean(destroyMethod = "destroy", autowire = Autowire.NO) // TODO What does it mean destroyMethod = "destroy", autowire = Autowire.NO?
    public SchedulerFactoryBean scheduler(JobFactory jobFactory, DataSource dataSource) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // Delegate Spring customized jobFactory to be managed by Scheduler
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setStartupDelay(2);
        schedulerFactoryBean.setAutoStartup(true);
        // Set dataSource to use the same data source with the application
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        // Way 1: If we configure Quartz using DB, then use the following:
        Properties quartzProperties = (Properties) configurableEnvironment.getPropertySources().get(Constants.CONFIG).getSource();
        schedulerFactoryBean.setQuartzProperties(quartzProperties);
//        // Way 2: If we configure Quartz using 'quartz.properties' file in class path, then use
//        // the following:
//        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
        return schedulerFactoryBean;
    }

    public Boolean getEnableScheduleJobOne() {
        return enableScheduleJobOne;
    }

    public void setEnableScheduleJobOne(Boolean enableScheduleJobOne) {
        this.enableScheduleJobOne = enableScheduleJobOne;
    }

    public String getScheduleJobOneCron() {
        return scheduleJobOneCron;
    }

    public void setScheduleJobOneCron(String scheduleJobOneCron) {
        this.scheduleJobOneCron = scheduleJobOneCron;
    }

    public Boolean getEnableScheduleJobTwo() {
        return enableScheduleJobTwo;
    }

    public void setEnableScheduleJobTwo(Boolean enableScheduleJobTwo) {
        this.enableScheduleJobTwo = enableScheduleJobTwo;
    }

    public String getScheduleJobTwoCron() {
        return scheduleJobTwoCron;
    }

    public void setScheduleJobTwoCron(String scheduleJobTwoCron) {
        this.scheduleJobTwoCron = scheduleJobTwoCron;
    }

    public Boolean getEnableScheduleJobThree() {
        return enableScheduleJobThree;
    }

    public void setEnableScheduleJobThree(Boolean enableScheduleJobThree) {
        this.enableScheduleJobThree = enableScheduleJobThree;
    }

    public String getScheduleJobThreeCron() {
        return scheduleJobThreeCron;
    }

    public void setScheduleJobThreeCron(String scheduleJobThreeCron) {
        this.scheduleJobThreeCron = scheduleJobThreeCron;
    }

    public Boolean getEnableDeleteHousekeepSummaryRecordJob() {
        return enableDeleteHousekeepSummaryRecordJob;
    }

    public void setEnableDeleteHousekeepSummaryRecordJob(Boolean enableDeleteHousekeepSummaryRecordJob) {
        this.enableDeleteHousekeepSummaryRecordJob = enableDeleteHousekeepSummaryRecordJob;
    }

    public Integer getHousekeepSummaryRecordRetentionPeriod() {
        return housekeepSummaryRecordRetentionPeriod;
    }

    public void setHousekeepSummaryRecordRetentionPeriod(Integer housekeepSummaryRecordRetentionPeriod) {
        this.housekeepSummaryRecordRetentionPeriod = housekeepSummaryRecordRetentionPeriod;
    }

    public String getDeleteHousekeepSummaryRecordJobCron() {
        return deleteHousekeepSummaryRecordJobCron;
    }

    public void setDeleteHousekeepSummaryRecordJobCron(String deleteHousekeepSummaryRecordJobCron) {
        this.deleteHousekeepSummaryRecordJobCron = deleteHousekeepSummaryRecordJobCron;
    }

    public ConfigurableEnvironment getConfigurableEnvironment() {
        return configurableEnvironment;
    }

    public void setConfigurableEnvironment(ConfigurableEnvironment configurableEnvironment) {
        this.configurableEnvironment = configurableEnvironment;
    }

    public Boolean getEnableArchiveActiveTableRecordJob() {
        return enableArchiveActiveTableRecordJob;
    }

    public void setEnableArchiveActiveTableRecordJob(Boolean enableArchiveActiveTableRecordJob) {
        this.enableArchiveActiveTableRecordJob = enableArchiveActiveTableRecordJob;
    }

    public Integer getActiveTableRecordRetentionPeriod() {
        return activeTableRecordRetentionPeriod;
    }

    public void setActiveTableRecordRetentionPeriod(Integer activeTableRecordRetentionPeriod) {
        this.activeTableRecordRetentionPeriod = activeTableRecordRetentionPeriod;
    }

    public String getArchiveActiveTableRecordJobCron() {
        return archiveActiveTableRecordJobCron;
    }

    public void setArchiveActiveTableRecordJobCron(String archiveActiveTableRecordJobCron) {
        this.archiveActiveTableRecordJobCron = archiveActiveTableRecordJobCron;
    }

}