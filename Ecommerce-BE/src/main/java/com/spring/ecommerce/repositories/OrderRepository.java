package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.Order;
import com.spring.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findByUser_Id(Long userId);

    @Query("SELECT COUNT(od) > 0 FROM OrderDetail od " +
            "LEFT JOIN Rating r ON od.order.id = r.orderDetailId AND od.userId = r.userId " +
            "WHERE od.userId = :userId AND od.product.id = :productId AND r.id IS NULL")
    boolean canUserComment(Long userId, Long productId);

    @Query("SELECT od.id FROM OrderDetail od " +
            "LEFT JOIN Rating r ON od.id = r.orderDetailId AND od.userId = r.userId " +
            "WHERE od.userId = :userId AND od.product.id = :productId AND r.id IS NULL")
    Long findFirstOrderDetailIdForRating(Long userId, Long productId);
}

