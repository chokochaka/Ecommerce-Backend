package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.category.CategoryDto;
import com.spring.ecommerce.dto.product.ProductDto;
import com.spring.ecommerce.dto.product.ReturnProductDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @PostMapping("/search")
    public List<ReturnProductDto> getProductItemsBySearch(@Valid @RequestBody SearchRequestDto searchRequestDto
    ) {
        return productService.getProductsBySearch(searchRequestDto);
    }

    @PostMapping("/search/paginated")
    public Page<ReturnProductDto> getProductsBySearchAndPagination(
            @Valid @RequestBody SearchRequestDto searchRequestDto
    ) {
        return productService.getProductsBySearchAndPagination(searchRequestDto);
    }

    @PostMapping
    public void createProduct(@Valid @RequestBody ProductDto productDto) {
        productService.createProduct(productDto);
    }

    @PutMapping("/{id}")
    public void updateProduct(
            @PathVariable long id,
            @Valid @RequestBody ProductDto productDto
    ) {
        productService.updateProduct(id, productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/{id}")
    public ReturnProductDto getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/category")
    public List<ReturnProductDto> getProductsByCategoryName(@Valid @RequestBody CategoryDto categoryDto) {
        return productService.getProductsByCategoryName(categoryDto.getName());
    }

}
