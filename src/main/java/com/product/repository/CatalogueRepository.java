package com.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.Catalogue;

@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Long> {

    public Optional<Catalogue> findByCatalogueId(Long catalogueId);

    public List<Catalogue> findByCatagory(Catalogue.CatagoryEnum catagory);

}