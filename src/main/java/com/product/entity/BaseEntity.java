package com.product.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_at")
    @NotNull(message = "createdAt cannot be null")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @NotNull(message = "updatedAt cannot be null")
    private LocalDateTime updatedAt;

    protected LocalDateTime getCreatedAt() {
        return createdAt;
    }

    protected void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    protected LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
