package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.product.ProductDto;
import com.spring.ecommerce.dto.product.ReturnProductDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.ProductMapper;
import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.repositories.CategoryRepository;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private FilterSpecificationService<Product> productFilterSpecificationService;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ReturnProductDto returnProductDto;
    private SearchRequestDto searchRequestDto;
    private ProductDto productDto;
    private Category category;

    @BeforeEach
    public void setup() {
        product = new Product();
        product.setId(1L);

        returnProductDto = ReturnProductDto.builder()
                .id(1L)
                .build();

        searchRequestDto = new SearchRequestDto();
        searchRequestDto.setPageRequestDto(new PageRequestDto());

        productDto = ProductDto.builder()
                .name("Product Name")
                .description("Product Description")
                .price(100.0)
                .isFeatured(true)
                .imageUrl("imageUrl")
                .categoryIds(Set.of(1L))
                .build();

        category = new Category();
        category.setId(1L);
    }

    @AfterEach
    public void tearDown() {
        reset(productRepository, productMapper, productFilterSpecificationService, categoryRepository);
    }

    @DisplayName("JUnit test for getProductsBySearch method")
    @Test
    public void getProductsBySearch_ShouldReturnProducts() {
        // Given
        Specification<Product> specification = mock(Specification.class);
        when(productFilterSpecificationService.getSearchSpecification(
                searchRequestDto.getFieldRequestDtos(), searchRequestDto.getGlobalOperator()
        )).thenReturn(specification);

        when(productRepository.findAll(specification)).thenReturn(Collections.singletonList(product));
        when(productMapper.productToReturnProductDto(product)).thenReturn(returnProductDto);

        // When
        List<ReturnProductDto> result = productService.getProductsBySearch(searchRequestDto);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(returnProductDto.getId());
    }

    @DisplayName("JUnit test for createProduct method")
    @Test
    public void createProduct_ShouldSaveProduct() {
        // Given
        when(productMapper.productDtoToProduct(productDto)).thenReturn(product);
        when(categoryRepository.findAllById(productDto.getCategoryIds())).thenReturn(Collections.singletonList(category));

        // When
        productService.createProduct(productDto);

        // Then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());
        Product savedProduct = productArgumentCaptor.getValue();

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getCategories()).hasSize(1);
    }

    @DisplayName("JUnit test for updateProduct method")
    @Test
    public void updateProduct_ShouldUpdateProduct() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findAllById(productDto.getCategoryIds())).thenReturn(Collections.singletonList(category));

        // When
        productService.updateProduct(1L, productDto);

        // Then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());
        Product updatedProduct = productArgumentCaptor.getValue();

        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo(productDto.getName());
        assertThat(updatedProduct.getCategories()).hasSize(1);
    }

    @DisplayName("JUnit test for deleteProduct method")
    @Test
    public void deleteProduct_ShouldDeleteProduct() {
        // Given
        long productId = 1L;

        // When
        productService.deleteProduct(productId);

        // Then
        verify(productRepository, times(1)).deleteById(productId);
    }

    @DisplayName("JUnit test for getProductById method")
    @Test
    public void getProductById_ShouldReturnProduct() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.productToReturnProductDto(product)).thenReturn(returnProductDto);

        // When
        ReturnProductDto result = productService.getProductById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(returnProductDto.getId());
    }

    @DisplayName("JUnit test for getProductsByCategoryName method")
    @Test
    public void getProductsByCategoryName_ShouldReturnProducts() {
        // Given
        when(categoryRepository.findCategoryByName("Category")).thenReturn(category);
        when(productRepository.findProductsByCategories(Set.of(category))).thenReturn(Collections.singletonList(product));
        when(productMapper.productToReturnProductDto(product)).thenReturn(returnProductDto);

        // When
        List<ReturnProductDto> result = productService.getProductsByCategoryName("Category");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(returnProductDto.getId());
    }
}
