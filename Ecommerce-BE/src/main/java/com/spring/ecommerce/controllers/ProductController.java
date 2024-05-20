package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.CreateProductWithProductItemDto;
import com.spring.ecommerce.mapper.ProductMapper;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductMapper productMapper;
    private final ProductService productService;
    private final FilterSpecificationService<Product> productItemFilterSpecificationService;

//    @PostMapping("/specification")
//    public List<Product> getProductsBySpecification(
//            @RequestBody RequestDto requestDto
//    ) {
//        Specification<Product> productItemSearchSpecification = productItemFilterSpecificationService
//                .getSearchSpecification(
//                        requestDto.getListSearchRequestDto()
//                        , requestDto.getGlobalOperator()
//                );
//        return productRepository.findAll(productItemSearchSpecification);
//    }

    @PostMapping("/create-with-product-items")
    public void createProduct(@RequestBody CreateProductWithProductItemDto createProductWithProductItemDto) {
        productService.createProductWithProductItems(createProductWithProductItemDto);
    }

}
