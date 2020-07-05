package com.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cart", schema = "product")
public class Cart extends BaseEntity implements Serializable, Cloneable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cart_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(name = "status", nullable = false)
    // TODO how to define default value in column definition
    @Enumerated(EnumType.STRING)
    @NotNull(message = "status cannot be null")
    // TODO https://www.baeldung.com/javax-validations-enums
    private CartStatusEnum status;

    @Column(name = "channel", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "channel cannot be null")
    private ChannelEnum channel;

    @Column(name = "quantity", nullable = false)
    @NotNull(message = "quantity cannot be null")
    private Integer quantity;

    @Column(name = "total_amount", nullable = false, precision = 3)
    @NotNull(message = "totalAmount cannot be null")
    @Digits(integer = 30, fraction = 3, message = "totalAmount is not valid")
    private BigDecimal totalAmount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id", nullable = false)
    @NotNull(message = "currency cannot be null")
    private Currency currency;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @NotNull(message = "user cannot be null")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", fetch = FetchType.EAGER)
    private Set<ProductItem> productItemList;

    public Cart() {
        super();
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public CartStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CartStatusEnum status) {
        this.status = status;
    }

    public ChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(ChannelEnum channel) {
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ProductItem> getProductItemList() {
        return productItemList;
    }

    public void setProductItemList(Set<ProductItem> productItemList) {
        this.productItemList = productItemList;
    }

    public int getProductItemNum() {
        return productItemNum;
    }

    public void setProductItemNum(int productItemNum) {
        this.productItemNum = productItemNum;
    }

    public Date getLatestUpdatedAt() {
        return latestUpdatedAt;
    }

    public void setLatestUpdatedAt(Date latestUpdatedAt) {
        this.latestUpdatedAt = latestUpdatedAt;
    }

    @Override
    public String toString() {
        return "Cart [cartId=" + cartId + ", status=" + status + ", channel=" + channel + ", quantity=" + quantity + ", totalAmount=" + totalAmount + ", currency=" + currency + ", user=" + user
                + ", productItemList=" + productItemList + ", createdAt()=" + getCreatedAt() + ", updatedAt()=" + getUpdatedAt() + "]";
    }

}