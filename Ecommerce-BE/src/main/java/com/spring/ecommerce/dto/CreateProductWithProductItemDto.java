package com.spring.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateProductWithProductItemDto {
    private CreateProductDto product;
    private List<AddProductItemToProductDto> productItems;
}
