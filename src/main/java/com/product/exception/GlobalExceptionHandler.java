package com.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.costant.Constants;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> errorHandler(Exception e) throws Exception {
        e.printStackTrace();
        ErrorBody errorBody = new ErrorBody();
        if (e instanceof ErrorResponseException) {
            errorBody.setSource(Constants.SOURCE);
            errorBody.setCode(((ErrorResponseException) e).getCode());
            errorBody.setMessage(((ErrorResponseException) e).getMessage());
            errorBody.setDetail(((ErrorResponseException) e).getDetail());
        } else if (e instanceof InputParameterException) {
            errorBody.setSource(Constants.SOURCE);
            errorBody.setCode(((InputParameterException) e).getCode());
            errorBody.setMessage(((InputParameterException) e).getMessage());
            errorBody.setDetail(((InputParameterException) e).getDetail());
        } else if (e instanceof TokenException) {
            errorBody.setSource(Constants.SOURCE);
            errorBody.setCode(((TokenException) e).getCode());
            errorBody.setMessage(((TokenException) e).getMessage());
            errorBody.setDetail(((TokenException) e).getDetail());
        } else {
            errorBody.setSource(Constants.SOURCE);
            errorBody.setMessage(Constants.UNEXPECTED_ERROR + " : " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(errorBody);
    }

}
