package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.product.ProductDto;
import com.spring.ecommerce.dto.product.ReturnProductDto;
import com.spring.ecommerce.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mapping(target = "averageRating", source = "averageRating")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "isFeatured", source = "featured")
    ReturnProductDto productToReturnProductDto(Product source);


    @Mapping(target = "averageRating", source = "averageRating")
//    @Mapping(target = "isFeatured", source = "isFeatured")
    Product productDtoToProduct(ProductDto destination);
}