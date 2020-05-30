package com.product.model.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductItemResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long productItemId;

    private String status;

    private CatalogueResp catalogueResp;

    private Integer quantity;

    private String channel;

    private BigDecimal totalAmount;

    private String currency;

    public ProductItemResp() {
        super();
    }

    public Long getProductItemId() {
        return productItemId;
    }

    public void setProductItemId(Long productItemId) {
        this.productItemId = productItemId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CatalogueResp getCatalogueResp() {
        return catalogueResp;
    }

    public void setCatalogueResp(CatalogueResp catalogueResp) {
        this.catalogueResp = catalogueResp;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "ProductItemResp [productItemId=" + productItemId + ", status=" + status + ", catalogueResp=" + catalogueResp + ", quantity=" + quantity + ", channel=" + channel
                + ", totalAmount=" + totalAmount + ", currency=" + currency + "]";
    }

}