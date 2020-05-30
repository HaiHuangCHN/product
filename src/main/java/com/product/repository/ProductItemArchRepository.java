package com.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.ProductItemArch;

@Repository
public interface ProductItemArchRepository extends JpaRepository<ProductItemArch, Long> {

}