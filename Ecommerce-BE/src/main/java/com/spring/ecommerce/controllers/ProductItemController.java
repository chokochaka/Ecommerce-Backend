package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.product.AddProductItemToProductDto;
import com.spring.ecommerce.dto.product.ProductItemDto;
import com.spring.ecommerce.dto.product.ReturnProductItemDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.ProductItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ReturnProductItemDto>> getProductItemsBySearch(@Valid @RequestBody SearchRequestDto searchRequestDto) {
        List<ReturnProductItemDto> productItems = productItemService.getProductItemsBySearch(searchRequestDto);
        return new ResponseEntity<>(productItems, HttpStatus.OK);
    }

    @PostMapping("/search/paginated")
    public ResponseEntity<Page<ReturnProductItemDto>> getProductItemsBySearchAndPagination(@Valid @RequestBody SearchRequestDto searchRequestDto) {
        Page<ReturnProductItemDto> productItems = productItemService.getProductItemsBySearchAndPagination(searchRequestDto);
        return new ResponseEntity<>(productItems, HttpStatus.OK);
    }

    @PostMapping // add product item to product
    public ResponseEntity<Void> addProductItemToProduct(@Valid @RequestBody AddProductItemToProductDto addProductItemToProductDto) {
        productItemService.addProductItemToProduct(addProductItemToProductDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProductItem(@PathVariable long id, @Valid @RequestBody ProductItemDto productItemDto) {
        productItemService.updateProductItem(id, productItemDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductItem(@PathVariable long id) {
        productItemService.deleteProductItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReturnProductItemDto>> getProductItemsByProductId(@PathVariable long productId) {
        List<ReturnProductItemDto> productItems = productItemService.getProductItemsByProductId(productId);
        return new ResponseEntity<>(productItems, HttpStatus.OK);
    }
}
