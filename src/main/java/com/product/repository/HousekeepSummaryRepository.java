package com.product.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.HousekeepSummary;

@Repository
public interface HousekeepSummaryRepository extends JpaRepository<HousekeepSummary, Integer> {

    public List<HousekeepSummary> deleteByUpdatedAtBefore(LocalDateTime updatedAt);

}
