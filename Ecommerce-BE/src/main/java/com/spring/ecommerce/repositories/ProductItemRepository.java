package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long>, JpaSpecificationExecutor<ProductItem> {
    List<ProductItem> findByVariationCombinationStartingWith(String size);

    @Transactional
    @Modifying
    @Query("update ProductItem p set p.price = :price, p.imageUrl = :imageUrl, p.availableStock = :stock, p.variationCombination = :variation, p.lastUpdatedOn = CURRENT_TIMESTAMP where p.id = :id")
    void updateProductItemById(@Param("price") double price, @Param("imageUrl") String imageUrl, @Param("stock") int availableStock, @Param("variation") String variationCombination, @Param("id") @NonNull Long id);

    @Query("SELECT pi FROM ProductItem pi WHERE pi.product.id = :productId")
    List<ProductItem> findByProductId(long productId);

    @Transactional
    @Modifying
    void deleteById(long id);

}
