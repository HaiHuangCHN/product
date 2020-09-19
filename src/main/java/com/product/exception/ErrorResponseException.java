package com.product.exception;

public class ErrorResponseException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6602852784285282083L;

    private final String code;

    private final String message;

    private final String detail;

    public ErrorResponseException(String code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }

}
