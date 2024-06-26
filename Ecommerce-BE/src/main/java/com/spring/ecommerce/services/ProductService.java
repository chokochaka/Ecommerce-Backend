package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.product.ProductDto;
import com.spring.ecommerce.dto.product.ReturnProductDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {


    List<ReturnProductDto> getProductsBySearch(SearchRequestDto searchRequestDto);

    Page<ReturnProductDto> getProductsBySearchAndPagination(SearchRequestDto searchRequestDto);

    void createProduct(ProductDto productDto); // admin - iv - usage

    void updateProduct(long id, ProductDto productDto);

    void deleteProduct(long id);

    ReturnProductDto getProductById(long id);

    List<ReturnProductDto> getProductsByCategoryName(String categoryName);
}
