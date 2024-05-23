package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.AddProductItemToProductDto;
import com.spring.ecommerce.dto.ProductItemDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.models.ProductItem;
import com.spring.ecommerce.services.ProductItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productItem")
@RequiredArgsConstructor
@Tag(name = "Product Item", description = "Product Item API")
public class ProductItemController {

    private final ProductItemService productItemService;
    private static final Logger logInfo = LoggerFactory.getLogger(ProductItemController.class);

    @PostMapping("/search")
    public List<ProductItem> getProductItemsBySearch(@RequestBody SearchRequestDto searchRequestDto
    ) {
        logInfo.debug("searchRequestDto: {}", searchRequestDto);
        return productItemService.getProductItemsBySearch(searchRequestDto);
    }

    @PostMapping("/search/paginated")
    public Page<ProductItem> getProductItemsBySearchAndPagination(
            @RequestBody SearchRequestDto searchRequestDto
    ) {
        return productItemService.getProductItemsBySearchAndPagination(searchRequestDto);
    }

    @PostMapping // add product item to product
    public void createProductItem(@RequestBody AddProductItemToProductDto addProductItemToProductDto) {
        productItemService.createProductItem(addProductItemToProductDto);
    }

    @PutMapping("/{id}")
    public void updateProductItem(
            @PathVariable long id,
            @RequestBody ProductItemDto productItemDto
    ) {
        productItemService.updateProductItem(id, productItemDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProductItem(@PathVariable long id) {
        productItemService.deleteProductItem(id);
    }

    @GetMapping("/product/{productId}")
    public List<ProductItem> getProductItemsByProductId(@PathVariable long productId) {
        return productItemService.getProductItemsByProductId(productId);
    }
}
