package com.product.controller;

import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.product.costant.Constants;
import com.product.exception.ErrorResponseException;
import com.product.exception.InputParameterException;
import com.product.model.request.AddProductItemReq;
import com.product.model.response.BookResp;
import com.product.model.response.CartResp;
import com.product.service.CartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(tags = "Cart API")
public class CartController {

    private static final Logger logger = LogManager.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @PutMapping(value = "/add/or/update/cart")
    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.OK, response = BookResp.class, responseContainer = "List"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.INTERNAL_SERVER_ERROR, response = ErrorResponseException.class) })
    @ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "User ID of the cart", example = "1", required = true, dataType = "long") })
    public ResponseEntity<CartResp> addOrUpdateCart(@RequestParam(value = "userId", required = true) Long userId) throws ErrorResponseException {
        CartResp cartResp = cartService.addOrUpdate(userId);
        logger.info(cartResp);
        return ResponseEntity.status(HttpStatus.SC_OK).body(cartResp);
    }

    @RequestMapping(value = "/get/cart", method = RequestMethod.GET)
    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.OK, response = BookResp.class, responseContainer = "List"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.INTERNAL_SERVER_ERROR, response = ErrorResponseException.class) })
    @ApiImplicitParams({ @ApiImplicitParam(name = "cartId", value = "ID of a cart", example = "1", required = true, dataType = "long") })
    public ResponseEntity<?> findBook(@RequestParam(value = "cartId", required = true) Long cartId) throws ErrorResponseException, InterruptedException {
        return ResponseEntity.status(HttpStatus.SC_OK).body(cartService.getByCartId(cartId));
    }

    @RequestMapping(value = "/add/product/item", method = RequestMethod.POST)
    public ResponseEntity<CartResp> allProductItem(@RequestBody @Valid AddProductItemReq addProductItemReq, Errors errors) throws ErrorResponseException, InputParameterException {
        cartService.validateInboundRequest(errors);
        return ResponseEntity.status(HttpStatus.SC_OK).body(cartService.addProductItem(addProductItemReq));
    }

    @RequestMapping(value = "/delete/product/item/{productItemId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProductItem(@PathVariable("productItemId") Long productItemId) throws ErrorResponseException {
        return ResponseEntity.status(HttpStatus.SC_OK).body(cartService.deleteProductItem(productItemId));
    }

}
