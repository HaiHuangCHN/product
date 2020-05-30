package com.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.UserArch;

@Repository
public interface UserArchRepository extends JpaRepository<UserArch, Long> {

}