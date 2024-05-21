package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {


    List<Product> getProductsBySearch(SearchRequestDto searchRequestDto);

    Page<Product> getProductsBySearchAndPagination(SearchRequestDto searchRequestDto);

    void createProduct(ProductDto productDto); // admin - iv - usage

    void updateProduct(long id, ProductDto productDto);

    void deleteProduct(long id);

    Product getProductById(long id);
}
