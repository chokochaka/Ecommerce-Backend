package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.CreateProductDto;
import com.spring.ecommerce.dto.CreateProductItemDto;
import com.spring.ecommerce.dto.ProductItemDto;
import com.spring.ecommerce.mapper.ProductItemMapper;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.ProductItem;
import com.spring.ecommerce.models.Stock;
import com.spring.ecommerce.repositories.ProductItemRepository;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.repositories.StockRepository;
import com.spring.ecommerce.services.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductItemServiceImpl implements ProductItemService {
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final ProductItemRepository productItemRepository;
    private final ProductItemMapper productItemMapper;

    @Override
    public void createProductItem(CreateProductItemDto createProductItemDto) {
        Product product = productRepository.findById(createProductItemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
//        Stock stock = Stock.builder()
//                .availableStock(createProductItemDto.getAvailableStock())
//                .totalStock(createProductItemDto.getAvailableStock())
//                .build();
        ProductItem productItem = ProductItem.builder()
                .product(product)
//                .stock(stock)
                .availableStock(createProductItemDto.getAvailableStock())
                .imageUrl(createProductItemDto.getImageUrl())
                .price(createProductItemDto.getPrice())
                .variationCombination(createProductItemDto.getVariationCombination())
                .build();
        productItemRepository.save(productItem);
    }

    @Override
    public void updateProductItem(long id, ProductItemDto productItemDto) {
        productItemRepository.updateProductItemById(
                productItemDto.getPrice(),
                productItemDto.getImageUrl(),
                productItemDto.getAvailableStock(),
                productItemDto.getVariationCombination(),
                id
        );
    }

    @Override
    public void deleteProductItem(long id) {
        productItemRepository.deleteById(id);
    }
}
