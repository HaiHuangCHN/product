package com.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.OrderDetail;
import com.product.entity.OrderDetailStatusEnum;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    public Optional<OrderDetail> findByStatus(OrderDetailStatusEnum status);

    public Optional<OrderDetail> findByOrderDetailId(long orderDetailId);

}