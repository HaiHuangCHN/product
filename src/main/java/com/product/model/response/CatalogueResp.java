package com.product.model.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class CatalogueResp {

    @JsonInclude(Include.NON_NULL)
    private Long catalogueId;

    @JsonInclude(Include.NON_NULL)
    private String displayName;

    @JsonInclude(Include.NON_NULL)
    private String catagory;

    @JsonInclude(Include.NON_NULL)
    private String status;

    @JsonInclude(Include.NON_NULL)
    private BigDecimal price;

    @JsonInclude(Include.NON_NULL)
    private String currency;

    @JsonInclude(Include.NON_NULL)
    private Integer quantity;

    private String shortDesc;

    private String longDesc;

    public CatalogueResp() {
        super();
    }

    public Long getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(Long catalogueId) {
        this.catalogueId = catalogueId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    @Override
    public String toString() {
        return "CatalogueResp [catalogueId=" + catalogueId + ", displayName=" + displayName + ", catagory=" + catagory + ", status=" + status + ", price=" + price + ", currency="
                + currency + ", quantity=" + quantity + ", shortDesc=" + shortDesc + ", longDesc=" + longDesc + "]";
    }

}
