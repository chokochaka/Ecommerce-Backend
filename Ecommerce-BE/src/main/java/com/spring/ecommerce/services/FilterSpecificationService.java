package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.search.RequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface FilterSpecificationService<T> {
    Specification<T> getSearchSpecification(
            List<SearchRequestDto> listSearchRequestDto,
            RequestDto.GlobalOperator globalOperator
    );

}

