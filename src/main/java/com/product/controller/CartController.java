package com.product.controller;

import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.product.costant.Constants;
import com.product.exception.ErrorResponseException;
import com.product.exception.InputParameterException;
import com.product.model.request.AddProductItemReq;
import com.product.model.response.BookResp;
import com.product.service.CartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(tags = "Cart API")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/add/or/update/cart", method = RequestMethod.PUT)
    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.OK, response = BookResp.class, responseContainer = "List"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.INTERNAL_SERVER_ERROR, response = ErrorResponseException.class) })
    @ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "User ID of the cart", example = "1", required = true, dataType = "long") })
    public ResponseEntity<?> addOrUpdateCart(@RequestParam(value = "userId", required = true) Long userId) throws ErrorResponseException {
        return ResponseEntity.status(HttpStatus.SC_OK).body(cartService.addOrUpdate(userId));
    }

    @RequestMapping(value = "/get/cart", method = RequestMethod.GET)
    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.OK, response = BookResp.class, responseContainer = "List"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.INTERNAL_SERVER_ERROR, response = ErrorResponseException.class) })
    @ApiImplicitParams({ @ApiImplicitParam(name = "cartId", value = "ID of a cart", example = "1", required = true, dataType = "long") })
    public ResponseEntity<?> findBook(@RequestParam(value = "cartId", required = true) Long cartId) throws ErrorResponseException, InterruptedException {
        return ResponseEntity.status(HttpStatus.SC_OK).body(cartService.getByCartId(cartId));
    }

    @RequestMapping(value = "/add/product/item", method = RequestMethod.POST)
    public ResponseEntity<?> allProductItem(@RequestBody @Valid AddProductItemReq addProductItemReq, Errors errors) throws ErrorResponseException, InputParameterException {
        cartService.validateInboundRequest(errors);
        return ResponseEntity.status(HttpStatus.SC_OK).body(cartService.addProductItem(addProductItemReq));
    }

    @RequestMapping(value = "/delete/product/item/{productItemId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProductItem(@PathVariable("productItemId") Long productItemId) throws ErrorResponseException {
        return ResponseEntity.status(HttpStatus.SC_OK).body(cartService.deleteProductItem(productItemId));
    }

}
