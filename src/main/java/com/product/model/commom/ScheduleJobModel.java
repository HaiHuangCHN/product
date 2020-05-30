package com.product.model.commom;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Schedule job request body", description = "Request to start/update/pause/resume/delete schedule job")
public class ScheduleJobModel {

    @ApiModelProperty(value = "Job group name of a Quartz schedule job", example = "group")
    @NotBlank(message = "cron is missing or blank")
    private String jobGroupName;

    @ApiModelProperty(value = "Job name of a Product schedule job", example = "job")
    @NotBlank(message = "cron is missing or blank")
    private String jobName;

    @ApiModelProperty(value = "Trigger group name of a Product schedule job", example = "group")
    @NotBlank(message = "cron is missing or blank")
    private String triggerGroupName;

    @ApiModelProperty(value = "Trigger name of a Product schedule job", example = "job")
    @NotBlank(message = "cron is missing or blank")
    private String triggerName;

    @ApiModelProperty(value = "cron expression of the job", example = "0/4 * * * * ?")
    @NotBlank(message = "cron is missing or blank")
    private String cron;

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

}