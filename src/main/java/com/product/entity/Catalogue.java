package com.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "catalogue", schema = "product")
public class Catalogue extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "catalogue_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catalogueId;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "catagory", nullable = false)
    @Enumerated(EnumType.STRING)
    private CatagoryEnum catagory;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "short_desc")
    private String shortDesc;

    @Column(name = "long_desc")
    private String longDesc;

    public static enum CatagoryEnum {
        ELECTRONICS, BOOK, CLOTHES, VIRTUAL_PRODUCT, FOOD, SHOES, OTHERS;
        private CatagoryEnum() {
        }
    }

    public static enum StatusEnum {
        AVAILABLE, NOT_AVAILABLE;
        private StatusEnum() {
        }
    }

    private Catalogue() {
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

    public CatagoryEnum getCatagory() {
        return catagory;
    }

    public void setCatagory(CatagoryEnum catagory) {
        this.catagory = catagory;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
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
        return "Catalogue [catalogueId=" + catalogueId + ", displayName=" + displayName + ", catagory=" + catagory + ", status=" + status + ", price=" + price + ", currency="
                + currency + ", quantity=" + quantity + ", shortDesc=" + shortDesc + ", longDesc=" + longDesc + ", createdAt()=" + getCreatedAt() + ", updatedAt()="
                + getUpdatedAt() + "]";
    }

}