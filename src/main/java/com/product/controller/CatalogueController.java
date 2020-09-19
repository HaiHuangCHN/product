package com.product.controller;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.product.costant.Constants;
import com.product.exception.ErrorResponseException;
import com.product.model.response.BookResp;
import com.product.service.CatalogueService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(tags = "Catalogue API")
public class CatalogueController {

    private static final Logger logger = LogManager.getLogger(CatalogueController.class);

    @Autowired
    private CatalogueService catalogueService;

    @RequestMapping(value = "/all/produts", method = RequestMethod.GET)
    public ResponseEntity<?> getCatalogue() {
        return ResponseEntity.status(HttpStatus.SC_OK).body(catalogueService.getCatalogue());
    }

    @RequestMapping(value = "/find/product", method = RequestMethod.GET)
    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.OK, response = BookResp.class, responseContainer = "List"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.INTERNAL_SERVER_ERROR, response = ErrorResponseException.class) })
    @ApiImplicitParams({ @ApiImplicitParam(name = "Catagory", value = "Catagory of a product", example = "ELECTRONICS", required = true) })
    public ResponseEntity<?> findBook(@RequestParam(value = "Catagory", required = true) String catagory) throws ErrorResponseException, InterruptedException {
        return ResponseEntity.status(HttpStatus.SC_OK).body(catalogueService.getByCatagory(catagory));
    }

}
