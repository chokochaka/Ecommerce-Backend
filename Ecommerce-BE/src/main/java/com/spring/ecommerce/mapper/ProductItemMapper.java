package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.product.AddProductItemToProductDto;
import com.spring.ecommerce.dto.product.ProductItemDto;
import com.spring.ecommerce.dto.product.ReturnProductItemDto;
import com.spring.ecommerce.models.ProductItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductItemMapper {

    List<ReturnProductItemDto> listProductItemToReturnProductItemDto(List<ProductItem> source);

//    Page<ReturnProductItemDto> listProductItemToReturnProductItemDto(Page<ProductItem> source);

    ReturnProductItemDto productItemToReturnProductItemDto(ProductItem source);

}
