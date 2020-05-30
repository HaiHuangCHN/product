package com.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    public List<Currency> findAll();

    public Currency findByCurrencyCode(String currencyCode);
}
