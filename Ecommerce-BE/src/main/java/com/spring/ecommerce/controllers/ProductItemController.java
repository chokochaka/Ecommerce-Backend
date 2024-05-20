package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.AddProductItemToProductDto;
import com.spring.ecommerce.dto.ProductItemDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
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
    public List<ProductItem> getProductItemsBySearch(@RequestBody SearchRequestDto searchRequestDto
    ) {
        return productItemService.getProductItemsBySearch(searchRequestDto);
    }

    @PostMapping("/search/paginated")
    public Page<ProductItem> getProductItemsBySearchAndPagination(
            @RequestBody SearchRequestDto searchRequestDto
    ) {
        return productItemService.getProductItemsBySearchAndPagination(searchRequestDto);
    }

    @PostMapping
    public void createProductItem(@RequestBody AddProductItemToProductDto addProductItemToProductDto) {
        productItemService.createProductItem(addProductItemToProductDto);
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
