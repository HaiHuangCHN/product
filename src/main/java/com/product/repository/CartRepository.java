package com.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.Cart;
import com.product.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    public Optional<Cart> findByUser(User user);

    public Optional<Cart> findByCartId(long cartId);

    public Cart deleteByCartId(Long cartId);

}