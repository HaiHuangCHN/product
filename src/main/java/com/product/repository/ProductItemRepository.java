package com.product.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.entity.Cart;
import com.product.entity.OrderDetail;
import com.product.entity.ProductItem;
import com.product.entity.ProductItemStatusEnum;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

    public Optional<ProductItem> findByProductItemId(long productItemId);

    public List<ProductItem> findByCart(Cart cart);

    public List<ProductItem> findByOrderDetail(OrderDetail orderDetail);

    public Optional<ProductItem> findByCartAndStatus(Cart cart, ProductItemStatusEnum status);

    public List<ProductItem> deleteByUpdatedAtBefore(LocalDateTime updatedAt);

}