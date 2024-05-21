package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.models.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto productToProductDto(Product source);

    @Mapping(target = "averageRating", source = "averageRating")
    @Mapping(target = "isFeatured", source = "featured")
    @InheritInverseConfiguration
    Product productDtoToProduct(ProductDto destination);
}