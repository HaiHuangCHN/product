package com.product.model.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CartResp implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long cartId;

    private String status;

    private String channel;

    private Integer quantity;

    private BigDecimal totalAmount;

    private String currency;

    private UserResp userResp;

    private List<ProductItemResp> productItemRespList;

    public CartResp() {
        super();
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public UserResp getUserResp() {
        return userResp;
    }

    public void setUser(UserResp userResp) {
        this.userResp = userResp;
    }

    public List<ProductItemResp> getProductItemRespList() {
        return productItemRespList;
    }

    public void setProductItemRespList(List<ProductItemResp> productItemRespList) {
        this.productItemRespList = productItemRespList;
    }

    @Override
    public String toString() {
        return "CartResp [cartId=" + cartId + ", status=" + status + ", channel=" + channel + ", quantity=" + quantity + ", totalAmount=" + totalAmount + ", currency=" + currency
                + ", user=" + userResp + ", productItemRespList=" + productItemRespList + "]";
    }

}