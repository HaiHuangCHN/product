//package com.product.config;
//
//import javax.validation.constraints.NotNull;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.validation.annotation.Validated;
//
//import com.product.util.RetentionPeriodValidation;
//
//@Validated
//@Configuration
//@ConfigurationProperties(prefix = "housekeep")
//@DependsOn("systemConfig")
//public class HousekeepJobConfig {
//
//    @NotNull
//    private Boolean enableHousekeepSummaryTableJob;
//
//    public Boolean getEnableHousekeepSummaryTableJob() {
//        return enableHousekeepSummaryTableJob;
//    }
//
//    public void setEnableHousekeepSummaryTableJob(Boolean enableHousekeepSummaryTableJob) {
//        this.enableHousekeepSummaryTableJob = enableHousekeepSummaryTableJob;
//    }
//
//    public Integer getSummaryTableRetentionPeriod() {
//        return summaryTableRetentionPeriod;
//    }
//
//    public void setSummaryTableRetentionPeriod(Integer summaryTableRetentionPeriod) {
//        this.summaryTableRetentionPeriod = summaryTableRetentionPeriod;
//    }
//
//}
