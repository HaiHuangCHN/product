package com.product.controller;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.product.costant.Constants;
import com.product.exception.ErrorResponseException;
import com.product.model.request.BookOrderReq;
import com.product.model.request.CreateOrderReq;
import com.product.model.response.BookResp;
import com.product.service.OrderDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(tags = "Order API")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping(value = "/get/order", method = RequestMethod.GET)
    public ResponseEntity<?> getOrder(@RequestParam(value = "orderDetailId", required = true) Long orderDetailId) throws ErrorResponseException {
        return ResponseEntity.status(HttpStatus.SC_OK).body(orderDetailService.findByOrderDetailId(orderDetailId));
    }

    @RequestMapping(value = "/create/order", method = RequestMethod.PUT)
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderReq createOrderReq) throws ErrorResponseException {
        return ResponseEntity.status(HttpStatus.SC_OK).body(orderDetailService.createOrder(createOrderReq));
    }

    @RequestMapping(value = "/book/order", method = RequestMethod.POST)
    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.OK, response = BookResp.class, responseContainer = "List"),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.INTERNAL_SERVER_ERROR, response = ErrorResponseException.class) })
    public ResponseEntity<?> bookOrder(@RequestBody BookOrderReq bookOrderReq) throws ErrorResponseException, InterruptedException {
        return ResponseEntity.status(HttpStatus.SC_OK).body(orderDetailService.updateOrder(bookOrderReq));
    }

    @RequestMapping(value = "/delete/order", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@RequestParam(value = "orderDetailId", required = true) Long orderDetailId) throws ErrorResponseException {
        orderDetailService.deleteByOrderDetailId(orderDetailId);
        return ResponseEntity.status(HttpStatus.SC_OK).body("Success");
    }

}
