package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.product.ReturnProductItemDto;
import com.spring.ecommerce.models.ProductItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductItemMapper {

    List<ReturnProductItemDto> listProductItemToReturnProductItemDto(List<ProductItem> source);

//    Page<ReturnProductItemDto> listProductItemToReturnProductItemDto(Page<ProductItem> source);

    ReturnProductItemDto productItemToReturnProductItemDto(ProductItem source);

}
