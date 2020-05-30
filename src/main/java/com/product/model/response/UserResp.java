package com.product.model.response;

import java.io.Serializable;

public class UserResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8351165037886187233L;

    private Long userId;

    private String firstname;

    private String lastname;

    public UserResp() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "UserResp [userId=" + userId + ", firstname=" + firstname + ", lastname=" + lastname + "]";
    }

}