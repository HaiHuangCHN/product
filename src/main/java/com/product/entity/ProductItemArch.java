package com.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_item_arch", schema = "product")
public class ProductItemArch extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "product_item_id", nullable = false)
    @NotNull(message = "productItemId cannot be null")
    private Long productItemId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "status cannot be null")
    private ProductItemStatusEnum status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catalogue_id", referencedColumnName = "catalogue_id", nullable = false)
    @NotNull(message = "catalogue cannot be null")
    private Catalogue catalogue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id", nullable = false)
    @NotNull(message = "cart cannot be null")
    private CartArch cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_detail_id", referencedColumnName = "order_detail_id")
    private OrderDetailArch orderDetail;

    @Column(name = "quantity", nullable = false)
    @NotNull(message = "quantity cannot be null")
    private Integer quantity;

    @Column(name = "channel", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "channel cannot be null")
    private ChannelEnum channel;

    @Column(name = "total_amount", nullable = false)
    @NotNull(message = "totalAmount cannot be null")
    private BigDecimal totalAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id", nullable = false)
    @NotNull(message = "currency cannot be null")
    private Currency currency;

    public ProductItemArch() {
        super();
    }

    public Long getProductItemId() {
        return productItemId;
    }

    public void setProductItemId(Long productItemId) {
        this.productItemId = productItemId;
    }

    public ProductItemStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProductItemStatusEnum status) {
        this.status = status;
    }

    public Catalogue getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Catalogue catalogue) {
        this.catalogue = catalogue;
    }

    public CartArch getCart() {
        return cart;
    }

    public void setCart(CartArch cart) {
        this.cart = cart;
    }

    public OrderDetailArch getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailArch orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(ChannelEnum channel) {
        this.channel = channel;
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

    @Override
    public String toString() {
        return "ProductItem [productItemId=" + productItemId + ", status=" + status + ", catalogue=" + catalogue + ", cart=" + cart + ", orderDetail=" + orderDetail + ", quantity="
                + quantity + ", channel=" + channel + ", totalAmount=" + totalAmount + ", currency=" + currency + ", createdAt()=" + getCreatedAt() + ", updatedAt()="
                + getUpdatedAt() + "]";
    }

}