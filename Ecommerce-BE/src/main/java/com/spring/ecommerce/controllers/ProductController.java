package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.CreateProductDto;
import com.spring.ecommerce.dto.CreateProductWithProductItemDto;
import com.spring.ecommerce.dto.ProductItemDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.ProductMapper;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.ProductService;
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

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductMapper productMapper;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final FilterSpecificationService<Product> productFilterSpecificationService;

//    @PostMapping("/search")
//    public List<Product> getProductsBySearch(
//            @RequestBody SearchRequestDto searchRequestDto
//    ) {
//        Specification<Product> productSearchSpecification = productFilterSpecificationService
//                .getSearchSpecification(
//                        searchRequestDto.getFieldRequestDtos()
//                        , searchRequestDto.getGlobalOperator()
//                );
//        return productRepository.findAll(productSearchSpecification);
//    }

    @PostMapping("/search/paginated")
    public Page<Product> getProductsBySearchAndPagination(
            @RequestBody SearchRequestDto searchRequestDto
    ) {
        Specification<Product> productSearchSpecification = productFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        Pageable pageable = new PageRequestDto().getPageable(searchRequestDto.getPageRequestDto());
        return productRepository.findAll(productSearchSpecification, pageable);
    }

    @PostMapping
    public void createProductItem(@RequestBody CreateProductDto createProductDto) {
        productService.createProduct(createProductDto);
    }

    @PutMapping("/{id}")
    public void updateProductItem(
            @PathVariable long id,
            @RequestBody ProductItemDto productItemDto
    ) {
//        productService.updateProductItem(id, productItemDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProductItem(@PathVariable long id) {
//        productService.deleteProductItem(id);
    }

    @PostMapping("/create-with-product-items")
    public void createProduct(@RequestBody CreateProductWithProductItemDto createProductWithProductItemDto) {
        productService.createProductWithProductItems(createProductWithProductItemDto);
    }

}
