package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.CreateProductItemDto;
import com.spring.ecommerce.dto.ProductItemDto;
import com.spring.ecommerce.models.ProductItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductItemMapper {
    List<CreateProductItemDto> listProductItemToProductItemDto(List<ProductItem> source);

    @InheritInverseConfiguration
    List<ProductItem> listProductItemDtoToProductItem(List<CreateProductItemDto> source);

    ProductItem productItemDtoToProductItem(ProductItemDto source);

    ProductItemDto productItemToProductItemDto(ProductItem source);
}
