package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.CreateProductItemDto;
import com.spring.ecommerce.dto.ProductItemDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.RequestDto;
import com.spring.ecommerce.models.ProductItem;
import com.spring.ecommerce.repositories.ProductItemRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productItem")
@RequiredArgsConstructor
public class ProductItemController {

    private final ProductItemRepository productItemRepository;
    private final ProductItemService productItemService;
    private final FilterSpecificationService<ProductItem> productItemFilterSpecificationService;

    @PostMapping("/search")
    public List<ProductItem> getProductsBySearch(
            @RequestBody RequestDto requestDto
    ) {
        Specification<ProductItem> productItemSearchSpecification = productItemFilterSpecificationService
                .getSearchSpecification(
                        requestDto.getSearchRequestDtos()
                        , requestDto.getGlobalOperator()
                );
        return productItemRepository.findAll(productItemSearchSpecification);
    }

    @PostMapping("/search/paginated")
    public Page<ProductItem> getProductsBySearchAndPagination(
            @RequestBody RequestDto requestDto
    ) {
        Specification<ProductItem> productItemSearchSpecification = productItemFilterSpecificationService
                .getSearchSpecification(
                        requestDto.getSearchRequestDtos()
                        , requestDto.getGlobalOperator()
                );
        Pageable pageable = new PageRequestDto().getPageable(requestDto.getPageRequestDto());
        return productItemRepository.findAll(productItemSearchSpecification, pageable);
    }

    @PostMapping
    public void createProductItem(@RequestBody CreateProductItemDto createProductItemDto) {
        productItemService.createProductItem(createProductItemDto);
    }

    @PutMapping("/{id}")
    public void updateProductItem(
            @PathVariable long id,
            @RequestBody ProductItemDto productItemDto
    ) {
        productItemService.updateProductItem(id, productItemDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProductItem(@PathVariable long id) {
        productItemService.deleteProductItem(id);
    }
}
