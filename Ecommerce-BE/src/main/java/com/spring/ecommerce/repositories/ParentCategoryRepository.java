package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.ParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentCategoryRepository extends JpaRepository<ParentCategory, Long>, JpaSpecificationExecutor<ParentCategory> {
}
