package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.search.RequestDto;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.ProductItem;
import com.spring.ecommerce.repositories.ProductItemRepository;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final FilterSpecificationService<Product> productItemFilterSpecificationService;

    @PostMapping("/specification")
    public List<Product> getProductsBySpecification(
            @RequestBody RequestDto requestDto
    ) {
        Specification<Product> productItemSearchSpecification = productItemFilterSpecificationService
                .getSearchSpecification(
                        requestDto.getListSearchRequestDto()
                        , requestDto.getGlobalOperator()
                );
        return productRepository.findAll(productItemSearchSpecification);
    }

}
