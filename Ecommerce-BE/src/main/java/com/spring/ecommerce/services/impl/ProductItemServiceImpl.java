package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.product.AddProductItemToProductDto;
import com.spring.ecommerce.dto.product.ProductItemDto;
import com.spring.ecommerce.dto.product.ReturnProductItemDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.ProductItemMapper;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.ProductItem;
import com.spring.ecommerce.models.VariationValue;
import com.spring.ecommerce.repositories.ProductItemRepository;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.repositories.StockRepository;
import com.spring.ecommerce.repositories.VariationValueRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductItemServiceImpl implements ProductItemService {
    private final ProductRepository productRepository;
    private final VariationValueRepository variationValueRepository;
    private final StockRepository stockRepository;
    private final ProductItemRepository productItemRepository;
    private final FilterSpecificationService<ProductItem> productItemFilterSpecificationService;
    private final ProductItemMapper productItemMapper;

    @Override
    public List<ReturnProductItemDto> getProductItemsBySearch(SearchRequestDto searchRequestDto) {
        Specification<ProductItem> productItemSearchSpecification = productItemFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        return productItemMapper.listProductItemToReturnProductItemDto(productItemRepository.findAll(productItemSearchSpecification));
    }

    @Override
    public Page<ReturnProductItemDto> getProductItemsBySearchAndPagination(SearchRequestDto searchRequestDto) {
        Specification<ProductItem> productItemSearchSpecification = productItemFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        Pageable pageable = new PageRequestDto().getPageable(searchRequestDto.getPageRequestDto());
        // convert Page<ProductItem> to Page<ReturnProductItemDto>
        return productItemRepository.findAll(productItemSearchSpecification, pageable)
                .map(productItemMapper::productItemToReturnProductItemDto);

//        return productItemMapper.listProductItemToReturnProductItemDto(productItemRepository.findAll(productItemSearchSpecification, pageable));
    }

    @Override
    public void addProductItemToProduct(AddProductItemToProductDto addProductItemToProductDto) {
        Product product = productRepository.findById(addProductItemToProductDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // variation
        String[] parts = addProductItemToProductDto.getVariationCombination().split(":");
        VariationValue size = variationValueRepository.findByName(parts[0]);
        VariationValue color = variationValueRepository.findByName(parts[1]);
        product.getVariationValues().add(size);
        product.getVariationValues().add(color);
        productRepository.save(product);

        ProductItem productItem = ProductItem.builder()
                .product(product)
                .availableStock(addProductItemToProductDto.getAvailableStock())
                .imageUrl(addProductItemToProductDto.getImageUrl())
                .price(addProductItemToProductDto.getPrice())
                .variationCombination(addProductItemToProductDto.getVariationCombination())
                .build();
        productItemRepository.save(productItem);
    }

    @Override
    public void updateProductItem(long id, ProductItemDto productItemDto) {
        ProductItem productItem = productItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Product item not found"));
        int prevStock = productItem.getAvailableStock();

        Product product = productRepository.findById(productItem.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // variation
        String[] parts = productItemDto.getVariationCombination().split(":");
        VariationValue size = variationValueRepository.findByName(parts[0]);
        VariationValue color = variationValueRepository.findByName(parts[1]);
        product.getVariationValues().add(size);
        product.getVariationValues().add(color);
        productRepository.save(product);

        productItem.setAvailableStock(productItemDto.getAvailableStock());
        productItem.setImageUrl(productItemDto.getImageUrl());
        productItem.setPrice(productItemDto.getPrice());
        productItem.setVariationCombination(productItemDto.getVariationCombination());
        productItem.getStock().setAvailableStock(productItemDto.getAvailableStock());

        // availableStock increased if new stock is greater than previous stock
        if (productItemDto.getAvailableStock() > prevStock) {
            productItem.getStock().setTotalStock(productItem.getStock().getTotalStock() + (productItemDto.getAvailableStock() - prevStock));
        }
        productItemRepository.save(productItem);
    }

    @Override
    public void deleteProductItem(long id) {
        productItemRepository.deleteById(id);
    }

    @Override
    public List<ReturnProductItemDto> getProductItemsByProductId(long productId) {
        return productItemMapper.listProductItemToReturnProductItemDto(productItemRepository.findByProductId(productId));
    }
}
