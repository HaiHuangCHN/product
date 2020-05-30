package com.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.Config;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {

    public List<Config> findAll();

    public List<Config> findByEnv(Config.EnvEnum env);
}
