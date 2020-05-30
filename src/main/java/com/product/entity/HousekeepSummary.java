package com.product.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "housekeep_summary")
public class HousekeepSummary extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "housekeep_summary_id")
    private Integer housekeepSummaryId;

    @Column(name = "scheduler_instance_name", nullable = false)
    private String schedulerInstanceName;

    @Column(name = "scheduled_job_name", nullable = false)
    private String scheduledJobName;

    @Column(name = "scheduled_job_started_at", nullable = false)
    private LocalDateTime scheduledJobStartedAt;

    @Column(name = "scheduled_job_finished_at", nullable = false)
    private LocalDateTime scheduledJobFinishedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status;

    @Column(name = "status_message")
    private String statusMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeEnum type;

    @Column(name = "remove_from", nullable = false)
    private String removeFrom;

    @Column(name = "copy_to", nullable = false)
    private String copyTo;

    @Column(name = "no_of_records_updated", nullable = false)
    private Long noOfRecordsUpdated;

    public static enum StatusEnum {
        SUCCESS, FAIL;
        private StatusEnum() {
        }
    }

    public static enum TypeEnum {
        DATABASE, FILE;
        private TypeEnum() {
        }
    }

    public HousekeepSummary() {
    }

    public HousekeepSummary(String schedulerInstanceName, String scheduledJobName, LocalDateTime scheduledJobStartedAt, LocalDateTime scheduledJobFinishedAt, StatusEnum status,
            TypeEnum type, String removeFrom, String copyTo, Long noOfRecordsUpdated, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.schedulerInstanceName = schedulerInstanceName;
        this.scheduledJobName = scheduledJobName;
        this.scheduledJobStartedAt = scheduledJobStartedAt;
        this.scheduledJobFinishedAt = scheduledJobFinishedAt;
        this.status = status;
        this.type = type;
        this.removeFrom = removeFrom;
        this.copyTo = copyTo;
        this.noOfRecordsUpdated = noOfRecordsUpdated;
        super.setCreatedAt(createdAt);
        super.setUpdatedAt(updatedAt);
    }

    public Integer getHousekeepSummaryId() {
        return housekeepSummaryId;
    }

    public void setHousekeepSummaryId(Integer housekeepSummaryId) {
        this.housekeepSummaryId = housekeepSummaryId;
    }

    public String getSchedulerInstanceName() {
        return schedulerInstanceName;
    }

    public void setSchedulerInstanceName(String schedulerInstanceName) {
        this.schedulerInstanceName = schedulerInstanceName;
    }

    public String getScheduledJobName() {
        return scheduledJobName;
    }

    public void setScheduledJobName(String scheduledJobName) {
        this.scheduledJobName = scheduledJobName;
    }

    public LocalDateTime getScheduledJobStartedAt() {
        return scheduledJobStartedAt;
    }

    public void setScheduledJobStartedAt(LocalDateTime scheduledJobStartedAt) {
        this.scheduledJobStartedAt = scheduledJobStartedAt;
    }

    public LocalDateTime getScheduledJobFinishedAt() {
        return scheduledJobFinishedAt;
    }

    public void setScheduledJobFinishedAt(LocalDateTime scheduledJobFinishedAt) {
        this.scheduledJobFinishedAt = scheduledJobFinishedAt;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public String getRemoveFrom() {
        return removeFrom;
    }

    public void setRemoveFrom(String removeFrom) {
        this.removeFrom = removeFrom;
    }

    public String getCopyTo() {
        return copyTo;
    }

    public void setCopyTo(String copyTo) {
        this.copyTo = copyTo;
    }

    public Long getNoOfRecordsUpdated() {
        return noOfRecordsUpdated;
    }

    public void setNoOfRecordsUpdated(Long noOfRecordsUpdated) {
        this.noOfRecordsUpdated = noOfRecordsUpdated;
    }

    @Override
    public String toString() {
        return "HousekeepSummary [housekeep_summary_id=" + housekeepSummaryId + ", schedulerInstanceName=" + schedulerInstanceName + ", scheduledJobName=" + scheduledJobName
                + ", scheduledJobStartedAt=" + scheduledJobStartedAt + ", scheduledJobFinishedAt=" + scheduledJobFinishedAt + ", status=" + status + ", statusMessage="
                + statusMessage + ", type=" + type + ", removeFrom=" + removeFrom + ", copyTo=" + copyTo + ", noOfRecordsUpdated=" + noOfRecordsUpdated + ", createdAt="
                + super.getCreatedAt() + ", updatedAt=" + super.getUpdatedAt() + "]";
    }

}
