package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.CreateProductDto;
import com.spring.ecommerce.models.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    CreateProductDto productToProductDto(Product source);

    @Mapping(target = "averageRating", source = "averageRating")
    @InheritInverseConfiguration
    Product productDtoToProduct(CreateProductDto destination);
}