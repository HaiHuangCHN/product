package com.product.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_arch", schema = "product")
public class UserArch extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id", nullable = false)
    @NotNull(message = "userId cannot be null")
    private Long userId;

    @Size(max = 25, message = "firstName exceed length constraint")
    @Column(name = "first_name", length = 25)
    private String firstName;

    @Size(max = 25, message = "lastName exceed length constraint")
    @Column(name = "last_name", length = 25)
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private CartArch cart;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<OrderDetailArch> orderDetailList;

    public UserArch() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<OrderDetailArch> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(Set<OrderDetailArch> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public CartArch getCart() {
        return cart;
    }

    public void setCart(CartArch cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", orderDetailList=" + orderDetailList + ", cart=" + cart + "]";
    }

}