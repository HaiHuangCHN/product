package com.product.costant;

public enum ScheduleJobEnum {

    PRODUCT_SCHEDULE_JOB_1("job1", "jobGroup1", "trigger1", "triggerGroup1", "job1 description"),
    PRODUCT_SCHEDULE_JOB_2("job2", "jobGroup2", "trigger2", "triggerGroup2", "job2 description"),
    PRODUCT_SCHEDULE_JOB_3("job3", "jobGroup3", "trigger3", "triggerGroup3", "job3 description"),
    DELETE_HOUSEKEEP_SUMMARY_RECORD_JOB("Delete Housekeep Summary Record Job", "Housekeep Job Group", "Delete Housekeep Summary Record Job Trigger", "Housekeep Job Trigger Group", "delete housekeep summary record job"),
    ARCHIVE_DATABASE_RECORD_JOB("Archive Database Record Job", "Housekeep Job Group", "Archive Database Record Job Trigger", "Housekeep Job Trigger Group", "archive database record job");

    private String jobName;
    private String jobGroupName;
    private String triggerName;
    private String triggerGroupName;
    private String description;

    ScheduleJobEnum(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String description) {
        this.jobName = jobName;
        this.jobGroupName = jobGroupName;
        this.triggerName = triggerName;
        this.triggerGroupName = triggerGroupName;
        this.description = description;
    }

    public String getJobName() {
        return jobName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }
    
    public String getDescription() {
        return description;
    }
    
}
