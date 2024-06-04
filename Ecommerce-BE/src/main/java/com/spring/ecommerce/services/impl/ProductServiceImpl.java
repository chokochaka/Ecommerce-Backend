package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.product.ProductDto;
import com.spring.ecommerce.dto.product.ReturnProductDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.ProductMapper;
import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.repositories.CategoryRepository;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final FilterSpecificationService<Product> productFilterSpecificationService;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ReturnProductDto> getProductsBySearch(SearchRequestDto searchRequestDto) {
        Specification<Product> productSearchSpecification = productFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        return productRepository.findAll(productSearchSpecification).stream()
                .map(productMapper::productToReturnProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReturnProductDto> getProductsBySearchAndPagination(SearchRequestDto searchRequestDto) {
        Specification<Product> productSearchSpecification = productFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        Pageable pageable = new PageRequestDto().getPageable(searchRequestDto.getPageRequestDto());
        Page<Product> productPage = productRepository.findAll(productSearchSpecification, pageable);
        return productPage.map(productMapper::productToReturnProductDto);
    }

    @Override
    public void createProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        List<Category> categoryList = categoryRepository.findAllById(productDto.getCategoryIds());
        Set<Category> categories = new HashSet<>(categoryList);
        product.setCategories(categories);

        productRepository.save(product);
    }

    @Override
    public void updateProduct(long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setFeatured(productDto.isFeatured());
        // categories
        Set<Long> categoryIds = productDto.getCategoryIds();
        List<Category> categoryList = categoryRepository.findAllById(categoryIds);
        Set<Category> categories = new HashSet<>(categoryList);
        product.setCategories(categories);

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ReturnProductDto getProductById(long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return productMapper.productToReturnProductDto(product);
    }

    @Override
    public List<ReturnProductDto> getProductsByCategoryName(String categoryName) {
        Category category = categoryRepository.findCategoryByName(categoryName);
        List<Product> products = productRepository.findProductsByCategories(Set.of(category));
        return products.stream()
                .map(productMapper::productToReturnProductDto)
                .collect(Collectors.toList());
    }
}
