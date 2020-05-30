package com.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.product.exception.ErrorResponseException;
import com.product.model.response.CatalogueResp;

@Service
public interface CatalogueService {

    public List<CatalogueResp> getCatalogue();

    public List<CatalogueResp> getByCatagory(String catagory);

}
