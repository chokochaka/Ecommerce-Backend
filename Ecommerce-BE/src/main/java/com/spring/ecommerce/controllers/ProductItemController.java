package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.search.RequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.models.ProductItem;
import com.spring.ecommerce.repositories.ProductItemRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.impl.FilterSpecificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productItem")
@RequiredArgsConstructor
public class ProductItemController {

    private final ProductItemRepository productItemRepository;
    private final FilterSpecificationService<ProductItem> productItemFilterSpecificationService;

    @PostMapping("/specification")
    public List<ProductItem> getProductsBySpecification(
            @RequestBody RequestDto requestDto
    ) {
        Specification<ProductItem> productItemSearchSpecification = productItemFilterSpecificationService
                .getSearchSpecification(
                        requestDto.getListSearchRequestDto()
                        , requestDto.getGlobalOperator()
                );
        return productItemRepository.findAll(productItemSearchSpecification);
    }

    @PostMapping("/create")
    public ProductItem createProductItem(@RequestBody ProductItem productItem) {
        return productItemRepository.save(productItem);
    }
}
