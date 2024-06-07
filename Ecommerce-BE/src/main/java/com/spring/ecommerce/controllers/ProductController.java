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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ReturnProductDto>> getProductItemsBySearch(@Valid @RequestBody SearchRequestDto searchRequestDto) {
        List<ReturnProductDto> products = productService.getProductsBySearch(searchRequestDto);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/search/paginated")
    public ResponseEntity<Page<ReturnProductDto>> getProductsBySearchAndPagination(@Valid @RequestBody SearchRequestDto searchRequestDto) {
        Page<ReturnProductDto> products = productService.getProductsBySearchAndPagination(searchRequestDto);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductDto productDto) {
        productService.createProduct(productDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable long id, @Valid @RequestBody ProductDto productDto) {
        productService.updateProduct(id, productDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnProductDto> getProductById(@PathVariable long id) {
        ReturnProductDto product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<List<ReturnProductDto>> getProductsByCategoryName(@Valid @RequestBody CategoryDto categoryDto) {
        List<ReturnProductDto> products = productService.getProductsByCategoryName(categoryDto.getName());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
