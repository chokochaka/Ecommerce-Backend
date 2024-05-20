package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.dto.CreateProductWithProductItemDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.ProductItemMapper;
import com.spring.ecommerce.mapper.ProductMapper;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.ProductItem;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductItemMapper productItemMapper;
    private final FilterSpecificationService<Product> productFilterSpecificationService;

    @Override
    public List<Product> getProductsBySearch(SearchRequestDto searchRequestDto) {
        Specification<Product> productSearchSpecification = productFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        return productRepository.findAll(productSearchSpecification);
    }

    @Override
    public Page<Product> getProductsBySearchAndPagination(SearchRequestDto searchRequestDto) {
        Specification<Product> productSearchSpecification = productFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        Pageable pageable = new PageRequestDto().getPageable(searchRequestDto.getPageRequestDto());
        return productRepository.findAll(productSearchSpecification, pageable);
    }

    @Override
    public void createProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        productRepository.save(product);
    }

    @Override
    public void updateProduct(long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setFeatured(productDto.isFeatured());
        productRepository.save(product);

    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public void createProductWithProductItems(CreateProductWithProductItemDto createProductWithProductItemDto) {
        Product product = productMapper.productDtoToProduct(createProductWithProductItemDto.getProduct());
        List<ProductItem> productItems = productItemMapper.listProductItemDtoToProductItem(createProductWithProductItemDto.getProductItems());
        product.getProductItems().addAll(productItems);
        productRepository.save(product);
    }
}
