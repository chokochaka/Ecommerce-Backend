package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.search.FieldRequestDto;
import com.spring.ecommerce.enums.GlobalOperator;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface FilterSpecificationService<T> {
    Specification<T> getSearchSpecification(
            List<FieldRequestDto> fieldRequestDtos,
            GlobalOperator globalOperator
    );

}

