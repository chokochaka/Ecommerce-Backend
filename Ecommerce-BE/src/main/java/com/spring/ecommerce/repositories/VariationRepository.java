package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.User;
import com.spring.ecommerce.models.Variation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VariationRepository extends JpaRepository<Variation, Long>, JpaSpecificationExecutor<Variation> {
}
