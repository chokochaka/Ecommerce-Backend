package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.product.AddProductItemToProductDto;
import com.spring.ecommerce.dto.product.ProductItemDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.models.ProductItem;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductItemService {

    List<ProductItem> getProductItemsBySearch(SearchRequestDto searchRequestDto);

    Page<ProductItem> getProductItemsBySearchAndPagination(SearchRequestDto searchRequestDto);

    void addProductItemToProduct(AddProductItemToProductDto addProductItemToProductDto);

    void updateProductItem(long id, ProductItemDto productItemDto);

    void deleteProductItem(long id);

    List<ProductItem> getProductItemsByProductId(long productId);
}
