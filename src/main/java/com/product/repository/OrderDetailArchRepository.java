package com.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.OrderDetailArch;

@Repository
public interface OrderDetailArchRepository extends JpaRepository<OrderDetailArch, Long> {

}