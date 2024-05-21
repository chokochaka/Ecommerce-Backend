package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.ProductMapper;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
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
public class ProductController {

    private final ProductMapper productMapper;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final FilterSpecificationService<Product> productFilterSpecificationService;

    @PostMapping("/search")
    public List<Product> getProductItemsBySearch(@RequestBody SearchRequestDto searchRequestDto
    ) {
        return productService.getProductsBySearch(searchRequestDto);
    }

    @PostMapping("/search/paginated")
    public Page<Product> getProductsBySearchAndPagination(
            @RequestBody SearchRequestDto searchRequestDto
    ) {
        return productService.getProductsBySearchAndPagination(searchRequestDto);
    }

    @PostMapping
    public void createProduct(@RequestBody ProductDto productDto) {
        productService.createProduct(productDto);
    }

    @PutMapping("/{id}")
    public void updateProduct(
            @PathVariable long id,
            @RequestBody ProductDto productDto
    ) {
        productService.updateProduct(id, productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

}
