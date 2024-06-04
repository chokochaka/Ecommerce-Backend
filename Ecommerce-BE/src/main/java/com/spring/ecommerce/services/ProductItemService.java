package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.product.AddProductItemToProductDto;
import com.spring.ecommerce.dto.product.ProductItemDto;
import com.spring.ecommerce.dto.product.ReturnProductItemDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.models.ProductItem;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductItemService {

    List<ReturnProductItemDto> getProductItemsBySearch(SearchRequestDto searchRequestDto);

    Page<ReturnProductItemDto> getProductItemsBySearchAndPagination(SearchRequestDto searchRequestDto);

    void addProductItemToProduct(AddProductItemToProductDto addProductItemToProductDto);

    void updateProductItem(long id, ProductItemDto productItemDto);

    void deleteProductItem(long id);

    List<ReturnProductItemDto> getProductItemsByProductId(long productId);
}
