package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.VariationValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VariationValueRepository extends JpaRepository<VariationValue, Long>, JpaSpecificationExecutor<VariationValue> {
    VariationValue findByName(String name);
}
