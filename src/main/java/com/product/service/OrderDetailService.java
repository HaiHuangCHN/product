package com.product.service;

import org.springframework.stereotype.Service;

import com.product.exception.ErrorResponseException;
import com.product.model.request.BookOrderReq;
import com.product.model.request.CreateOrderReq;
import com.product.model.response.OrderDetailResp;

@Service
public interface OrderDetailService {

    public OrderDetailResp findByOrderDetailId(Long orderDetailId) throws ErrorResponseException;

    public OrderDetailResp createOrder(CreateOrderReq createOrderReq) throws ErrorResponseException;

    public OrderDetailResp updateOrder(BookOrderReq bookOrderReq) throws ErrorResponseException;

    public void deleteByOrderDetailId(Long orderDetailId) throws ErrorResponseException;

}
