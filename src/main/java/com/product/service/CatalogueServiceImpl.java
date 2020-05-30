package com.product.service;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.data.DataProvider;
import com.product.exception.ErrorResponseException;
import com.product.model.response.CatalogueResp;

@Service
public class CatalogueServiceImpl implements CatalogueService {

    private static final Logger logger = LoggerFactory.getLogger(CatalogueServiceImpl.class);

    @Autowired
    private DataProvider dataProvider;

    @Override
    public List<CatalogueResp> getCatalogue() {
        List<CatalogueResp> catalogueRespList = new LinkedList<CatalogueResp>();
        dataProvider.getCatalogue().forEach(x -> {
            CatalogueResp catalogueResp = new CatalogueResp();
            catalogueResp.setCatalogueId(x.getCatalogueId());
            catalogueResp.setDisplayName(x.getDisplayName());
            catalogueResp.setCatagory(x.getCatagory().toString());
            catalogueResp.setPrice(x.getPrice());
            catalogueResp.setCurrency(x.getCurrency().getCurrencyCode());
            catalogueResp.setQuantity(x.getQuantity());
            catalogueResp.setStatus(x.getStatus().toString());
            catalogueResp.setShortDesc(x.getShortDesc());
            catalogueResp.setLongDesc(x.getLongDesc());
            catalogueRespList.add(catalogueResp);
        });
        logger.info(catalogueRespList.toString());
        return catalogueRespList;
    }

    @Override
    public List<CatalogueResp> getByCatagory(String catagory) {
        List<CatalogueResp> catalogueRespList = new LinkedList<CatalogueResp>();
        dataProvider.getCatalogueByCatagory(catagory).forEach(x -> {
            CatalogueResp catalogueResp = new CatalogueResp();
            catalogueResp.setCatalogueId(x.getCatalogueId());
            catalogueResp.setDisplayName(x.getDisplayName());
            catalogueResp.setCatagory(x.getCatagory().toString());
            catalogueResp.setPrice(x.getPrice());
            catalogueResp.setCurrency(x.getCurrency().getCurrencyCode());
            catalogueResp.setStatus(x.getStatus().toString());
            catalogueResp.setQuantity(x.getQuantity());
            catalogueResp.setShortDesc(x.getShortDesc());
            catalogueResp.setLongDesc(x.getLongDesc());
            catalogueRespList.add(catalogueResp);
        });
        return catalogueRespList;
    }

}
