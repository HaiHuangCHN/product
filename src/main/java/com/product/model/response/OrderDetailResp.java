package com.product.model.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderDetailResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long orderDetailId;

    private String status;

    private String channel;

    private BigDecimal totalAmount;

    private String currency;

    private BigDecimal paymentAuthorizedAmount;

    private String paymentAuthorizedCurrency;

    private String paymentMethod;

    private String paymentProvider;

    private String paymentPlatform;

    private String paymentStatus;

    private List<ProductItemResp> productItemRespList;

    public OrderDetailResp() {
        super();
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPaymentAuthorizedAmount() {
        return paymentAuthorizedAmount;
    }

    public void setPaymentAuthorizedAmount(BigDecimal paymentAuthorizedAmount) {
        this.paymentAuthorizedAmount = paymentAuthorizedAmount;
    }

    public String getPaymentAuthorizedCurrency() {
        return paymentAuthorizedCurrency;
    }

    public void setPaymentAuthorizedCurrency(String paymentAuthorizedCurrency) {
        this.paymentAuthorizedCurrency = paymentAuthorizedCurrency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public String getPaymentPlatform() {
        return paymentPlatform;
    }

    public void setPaymentPlatform(String paymentPlatform) {
        this.paymentPlatform = paymentPlatform;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<ProductItemResp> getProductItemRespList() {
        return productItemRespList;
    }

    public void setProductItemRespList(List<ProductItemResp> productItemRespList) {
        this.productItemRespList = productItemRespList;
    }

    @Override
    public String toString() {
        return "OrderDetailResp [orderDetailId=" + orderDetailId + ", status=" + status + ", channel=" + channel + ", totalAmount=" + totalAmount + ", currency=" + currency
                + ", paymentAuthorizedAmount=" + paymentAuthorizedAmount + ", paymentAuthorizedCurrency=" + paymentAuthorizedCurrency + ", paymentMethod=" + paymentMethod
                + ", paymentProvider=" + paymentProvider + ", paymentPlatform=" + paymentPlatform + ", paymentStatus=" + paymentStatus + ", productItemRespList="
                + productItemRespList + "]";
    }

}