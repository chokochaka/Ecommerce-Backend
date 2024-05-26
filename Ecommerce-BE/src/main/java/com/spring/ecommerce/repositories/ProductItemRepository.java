package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long>, JpaSpecificationExecutor<ProductItem> {
    @Query("SELECT pi FROM ProductItem pi WHERE pi.product.id = :productId")
    List<ProductItem> findByProductId(long productId);
}
