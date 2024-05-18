package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.CreateProductWithProductItemDto;
import com.spring.ecommerce.mapper.ProductItemMapper;
import com.spring.ecommerce.mapper.ProductMapper;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.ProductItem;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductItemMapper productItemMapper;

    @Override
    public void createProductWithProductItems(CreateProductWithProductItemDto createProductWithProductItemDto) {
        Product product = productMapper.productDtoToProduct(createProductWithProductItemDto.getProduct());
        List<ProductItem> productItems = productItemMapper.listProductItemDtoToProductItem(createProductWithProductItemDto.getProductItems());
        product.getProductItems().addAll(productItems);
        productRepository.save(product);
    }
}
