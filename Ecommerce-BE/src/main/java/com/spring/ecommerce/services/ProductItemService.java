package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.AddProductItemToProductDto;
import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.dto.ProductItemDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.models.ProductItem;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductItemService {

    List<ProductItem> getProductItemsBySearch(SearchRequestDto searchRequestDto);

    Page<ProductItem> getProductItemsBySearchAndPagination(SearchRequestDto searchRequestDto);

    void createProductItem(AddProductItemToProductDto addProductItemToProductDto);

    void updateProductItem(long id, ProductItemDto productItemDto);

    void deleteProductItem(long id);

    List<ProductItem> getProductItemsByProductId(long productId);
}
