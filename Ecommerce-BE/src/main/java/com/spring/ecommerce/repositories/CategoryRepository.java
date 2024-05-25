package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM products_categories WHERE category_id = :categoryId", nativeQuery = true)
    void deleteProductCategoriesByCategoryId(long categoryId);

    // jpa query to find category by name
    @Query("SELECT c FROM Category c WHERE c.name = :name")
    Category findCategoryByName(@Param("name") String name);
}
