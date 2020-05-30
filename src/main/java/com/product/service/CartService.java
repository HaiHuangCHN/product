package com.product.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.product.exception.ErrorResponseException;
import com.product.exception.InputParameterException;
import com.product.model.request.AddProductItemReq;
import com.product.model.response.CartResp;

@Service
public interface CartService {

    public CartResp addOrUpdate(Long userId) throws ErrorResponseException;

    public CartResp getByCartId(Long cartId) throws ErrorResponseException;

    public CartResp addProductItem(AddProductItemReq addProductItemReq) throws ErrorResponseException;

    public CartResp deleteProductItem(Long productItemId) throws ErrorResponseException;

    public void validateInboundRequest(Errors errors) throws InputParameterException;

}
