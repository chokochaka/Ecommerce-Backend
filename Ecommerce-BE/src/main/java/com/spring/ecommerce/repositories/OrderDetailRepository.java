package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.Order;
import com.spring.ecommerce.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, JpaSpecificationExecutor<OrderDetail> {
    // find by user id
    List<OrderDetail> findByUserId(long userId);
}

