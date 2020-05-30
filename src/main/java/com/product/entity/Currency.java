package com.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency", schema = "product")
public class Currency extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "currency_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currencyId;

    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "decimal_points", nullable = false)
    private Integer decimalPoints;

    private Currency() {
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Integer getDecimalPoints() {
        return decimalPoints;
    }

    public void setDecimalPoints(Integer decimalPoints) {
        this.decimalPoints = decimalPoints;
    }

    @Override
    public String toString() {
        return "Currency [currencyId=" + currencyId + ", currencyCode=" + currencyCode + ", currencyName=" + currencyName + ", decimalPoints=" + decimalPoints + ", createdAt()="
                + getCreatedAt() + ", updatedAt()=" + getUpdatedAt() + "]";
    }

}