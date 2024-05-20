package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.CreateProductDto;
import com.spring.ecommerce.dto.CreateProductWithProductItemDto;

public interface ProductService {

    void createProduct(CreateProductDto createProductDto); // admin - iv - usage

    void createProductWithProductItems(CreateProductWithProductItemDto createProductWithProductItemDto); // admin - iv - usage
}
