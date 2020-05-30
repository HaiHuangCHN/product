package com.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "order_detail", schema = "product")
public class OrderDetail extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "order_detail_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "status cannot be null")
    private OrderDetailStatusEnum status;

    @Column(name = "channel", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "channel cannot be null")
    private ChannelEnum channel;

    @Column(name = "total_amount", nullable = false, precision = 3)
    // TODO what does scale mean in @Column?
    @NotNull(message = "totalAmount cannot be null")
    private BigDecimal totalAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id", nullable = false)
    @NotNull(message = "currency cannot be null")
    private Currency currency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @NotNull(message = "user cannot be null")
    private User user;

    @Column(name = "payment_authorized_amount")
    private BigDecimal paymentAuthorizedAmount;

    @Column(name = "payment_authorized_currency", length = 3)
    @Size(max = 3, message = "paymentAuthorizedCurrency exceed constraint")
    private String paymentAuthorizedCurrency;

    @Column(name = "payment_method", length = 255)
    @Size(max = 255, message = "paymentAuthorizedCurrency exceed constraint")
    private String paymentMethod;

    @Column(name = "payment_provider", length = 255)
    @Size(max = 255, message = "paymentAuthorizedCurrency exceed constraint")
    private String paymentProvider;

    @Column(name = "payment_platform", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderDetailPaymentPlatformEnum paymentPlatform;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private OrderDetailPaymentStatusEnum paymentStatus;

    @Column(name = "payment_last_updated_at")
    private LocalDateTime paymentLastUpdatedAt;

    @Column(name = "capture_status")
    @Enumerated(EnumType.STRING)
    private OrderDetailCaptureStatusEnum captureStatus;

    @Column(name = "capture_failure_reason")
    private String captureFailureReason;

    @Column(name = "capture_at")
    private LocalDateTime captureAt;

    @Column(name = "capture_retry_times")
    private Integer captureRetryTimes;

    @Column(name = "process_lock")
    @Enumerated(EnumType.STRING)
    private OrderDetailProcessLockEnum processLock;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderDetail", fetch = FetchType.EAGER)
    private Set<ProductItem> productItemList;

    public OrderDetail() {
        super();
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public OrderDetailStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderDetailStatusEnum status) {
        this.status = status;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public OrderDetailCaptureStatusEnum getCaptureStatus() {
        return captureStatus;
    }

    public void setCaptureStatus(OrderDetailCaptureStatusEnum captureStatus) {
        this.captureStatus = captureStatus;
    }

    public String getCaptureFailureReason() {
        return captureFailureReason;
    }

    public void setCaptureFailureReason(String captureFailureReason) {
        this.captureFailureReason = captureFailureReason;
    }

    public LocalDateTime getCaptureAt() {
        return captureAt;
    }

    public void setCaptureAt(LocalDateTime captureAt) {
        this.captureAt = captureAt;
    }

    public Integer getCaptureRetryTimes() {
        return captureRetryTimes;
    }

    public void setCaptureRetryTimes(Integer captureRetryTimes) {
        this.captureRetryTimes = captureRetryTimes;
    }

    public OrderDetailProcessLockEnum getProcessLock() {
        return processLock;
    }

    public void setProcessLock(OrderDetailProcessLockEnum processLock) {
        this.processLock = processLock;
    }

    public Set<ProductItem> getProductItemList() {
        return productItemList;
    }

    public void setProductItemList(Set<ProductItem> productItemList) {
        this.productItemList = productItemList;
    }

    @Override
    public String toString() {
        return "OrderDetail [orderDetailId=" + orderDetailId + ", status=" + status + ", channel=" + channel + ", totalAmount=" + totalAmount + ", currency=" + currency + ", user="
                + user + ", paymentAuthorizedAmount=" + paymentAuthorizedAmount + ", paymentAuthorizedCurrency=" + paymentAuthorizedCurrency + ", paymentMethod=" + paymentMethod
                + ", paymentProvider=" + paymentProvider + ", paymentPlatform=" + paymentPlatform + ", paymentStatus=" + paymentStatus + ", paymentLastUpdatedAt="
                + paymentLastUpdatedAt + ", captureStatus=" + captureStatus + ", captureFailureReason=" + captureFailureReason + ", captureAt=" + captureAt + ", captureRetryTimes="
                + captureRetryTimes + ", processLock=" + processLock + ", productItemList=" + productItemList + ", createdAt()=" + getCreatedAt() + ", updatedAt()="
                + getUpdatedAt() + "]";
    }

}