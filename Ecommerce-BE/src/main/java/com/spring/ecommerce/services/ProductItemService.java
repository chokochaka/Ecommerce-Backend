package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.CreateProductDto;
import com.spring.ecommerce.dto.CreateProductItemDto;
import com.spring.ecommerce.dto.ProductItemDto;

public interface ProductItemService {
    void createProductItem(CreateProductItemDto createProductItemDto);

    void updateProductItem(long id, ProductItemDto productItemDto);

    void deleteProductItem(long id);
}
