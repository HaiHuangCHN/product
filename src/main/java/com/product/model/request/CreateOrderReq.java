package com.product.model.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class CreateOrderReq {

    private Long userId;

    private List<Long> productItemIdList;

    private String channel;

    private BigDecimal totalAmount;

    private String currency;

    private String platform;

    public CreateOrderReq() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getProductItemIdList() {
        return productItemIdList;
    }

    public void setProductItemIdList(List<Long> productItemIdList) {
        this.productItemIdList = productItemIdList;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "CreateOrderReq [userId=" + userId + ", productItemIdList=" + productItemIdList + ", channel=" + channel + ", totalAmount=" + totalAmount + ", currency=" + currency
                + ", platform=" + platform + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CreateOrderReq createOrderReq = (CreateOrderReq) o;
        return Objects.equals(userId, createOrderReq.userId) && Objects.equals(productItemIdList, createOrderReq.productItemIdList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productItemIdList, totalAmount);
    }

}
