package com.product.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.product.entity.OrderDetailPaymentPlatformEnum;
import com.product.entity.OrderDetailPaymentStatusEnum;

public class BookOrderReq {

    private Long orderDetailId;

    private BigDecimal paymentAuthorizedAmount;

    private String paymentAuthorizedCurrency;

    private String paymentMethod;

    private String paymentProvider;

    private OrderDetailPaymentPlatformEnum paymentPlatform;

    private OrderDetailPaymentStatusEnum paymentStatus;

    private LocalDateTime paymentLastUpdatedAt;

    public BookOrderReq() {
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
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

    public OrderDetailPaymentPlatformEnum getPaymentPlatform() {
        return paymentPlatform;
    }

    public void setPaymentPlatform(OrderDetailPaymentPlatformEnum paymentPlatform) {
        this.paymentPlatform = paymentPlatform;
    }

    public OrderDetailPaymentStatusEnum getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(OrderDetailPaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentLastUpdatedAt() {
        return paymentLastUpdatedAt;
    }

    public void setPaymentLastUpdatedAt(LocalDateTime paymentLastUpdatedAt) {
        this.paymentLastUpdatedAt = paymentLastUpdatedAt;
    }

    @Override
    public String toString() {
        return "BookOrderReq [orderDetailId=" + orderDetailId + ", paymentAuthorizedAmount=" + paymentAuthorizedAmount + ", paymentAuthorizedCurrency=" + paymentAuthorizedCurrency
                + ", paymentMethod=" + paymentMethod + ", paymentProvider=" + paymentProvider + ", paymentPlatform=" + paymentPlatform + ", paymentStatus=" + paymentStatus
                + ", paymentLastUpdatedAt=" + paymentLastUpdatedAt + "]";
    }

}
