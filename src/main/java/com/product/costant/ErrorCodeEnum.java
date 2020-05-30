package com.product.costant;

public enum ErrorCodeEnum {
	BOOK_NOT_FOUND(200, "PD_001", "Book not found", ""),
	FAIL_VERIFY_TOKEN(200, "PD_002", "Fail to verify token", ""),
	INVALID_CLAIM(200, "PD_003", "Invalid claim", ""),
	// Catalogue
	PRODUCT_NOT_FOUND(200, "PD_003", "Nothing not found", ""),
    PRODUCT_ITEM_NOT_FOUND(200, "PD_004", "Nothing not found", ""),
    ORDER_NOT_FOUND(200, "PD_005", "Order not found", ""),
    CART_NOT_FOUND(200, "PD_006", "Cart not found", ""),
    CATALOGUE_NOT_FOUND(200, "PD_007", "Product not found", ""),
    // General
    INCOMPLETE_REQUEST_BODY(200, "PD_999", "Invalid request body", "");

	ErrorCodeEnum(int httpStatusCode, String selfDefinedCode, String msg, String detail) {
		this.httpStatusCode = httpStatusCode;
		this.selfDefinedCode = selfDefinedCode;
		this.message = msg;
		this.detail = detail;
	}

	public int httpStatusCode;
	public String selfDefinedCode;
	public String message;
	public String detail;

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	/*
	 * Disable setter as we want the httpStatusCode to be hardcode
	 */
//	public void setHttpStatusCode(int httpStatusCode) {
//		this.httpStatusCode = httpStatusCode;
//	}

	public String getSelfDefinedCode() {
		return selfDefinedCode;
	}

	/*
	 * Disable setter as we want the httpStatusCode to be hardcode
	 */
//	public void setSelfDefinedCode(String selfDefinedCode) {
//		this.selfDefinedCode = selfDefinedCode;
//	}

	public String getMessage() {
		return message;
	}

	/*
	 * Disable setter as we want the httpStatusCode to be hardcode
	 */
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
