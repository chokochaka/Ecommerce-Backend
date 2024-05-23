package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.ProductDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final FilterSpecificationService<Product> productFilterSpecificationService;
    private final CategoryRepository categoryRepository;

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
        // I have list of categories ids need to add to the product, example ids: [1,2,3,4,5] mean that product will have 5 categories
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
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow();
    }
}
