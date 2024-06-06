package com.spring.ecommerce.services;

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
import com.spring.ecommerce.services.impl.ProductItemServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductItemServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private VariationValueRepository variationValueRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ProductItemRepository productItemRepository;

    @Mock
    private FilterSpecificationService<ProductItem> productItemFilterSpecificationService;

    @Mock
    private ProductItemMapper productItemMapper;

    @InjectMocks
    private ProductItemServiceImpl productItemService;

    private Product product;
    private ProductItem productItem;
    private ReturnProductItemDto returnProductItemDto;
    private SearchRequestDto searchRequestDto;
    private AddProductItemToProductDto addProductItemToProductDto;
    private ProductItemDto productItemDto;
    private VariationValue size;
    private VariationValue color;

    @BeforeEach
    public void setup() {
        product = new Product();
        product.setId(1L);

        productItem = new ProductItem();
        productItem.setId(1L);
        productItem.setProduct(product);

        returnProductItemDto = ReturnProductItemDto.builder()
                .id(1L)
                .build();

        searchRequestDto = new SearchRequestDto();
        searchRequestDto.setPageRequestDto(new PageRequestDto());

        addProductItemToProductDto = AddProductItemToProductDto.builder()
                .productId(1L)
                .availableStock(10)
                .imageUrl("imageUrl")
                .price(100.0)
                .variationCombination("size:color")
                .build();

        productItemDto = ProductItemDto.builder()
                .availableStock(15)
                .imageUrl("updatedImageUrl")
                .price(150.0)
                .variationCombination("newSize:newColor")
                .build();

        size = new VariationValue();
        size.setName("size");

        color = new VariationValue();
        color.setName("color");
    }

    @AfterEach
    public void tearDown() {
        reset(productRepository, variationValueRepository, stockRepository, productItemRepository, productItemFilterSpecificationService, productItemMapper);
    }

    @DisplayName("JUnit test for getProductItemsBySearch method")
    @Test
    public void getProductItemsBySearch_ShouldReturnProductItems() {
        // Given
        Specification<ProductItem> specification = mock(Specification.class);
        when(productItemFilterSpecificationService.getSearchSpecification(
                searchRequestDto.getFieldRequestDtos(), searchRequestDto.getGlobalOperator()
        )).thenReturn(specification);

        when(productItemRepository.findAll(specification)).thenReturn(Collections.singletonList(productItem));
        when(productItemMapper.listProductItemToReturnProductItemDto(Collections.singletonList(productItem))).thenReturn(Collections.singletonList(returnProductItemDto));

        // When
        List<ReturnProductItemDto> result = productItemService.getProductItemsBySearch(searchRequestDto);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(returnProductItemDto.getId());
    }


    @DisplayName("JUnit test for deleteProductItem method")
    @Test
    public void deleteProductItem_ShouldDeleteProductItem() {
        // Given
        long productItemId = 1L;

        // When
        productItemService.deleteProductItem(productItemId);

        // Then
        verify(productItemRepository, times(1)).deleteById(productItemId);
    }

    @DisplayName("JUnit test for getProductItemsByProductId method")
    @Test
    public void getProductItemsByProductId_ShouldReturnProductItems() {
        // Given
        long productId = 1L;
        when(productItemRepository.findByProductId(productId)).thenReturn(Collections.singletonList(productItem));
        when(productItemMapper.listProductItemToReturnProductItemDto(Collections.singletonList(productItem))).thenReturn(Collections.singletonList(returnProductItemDto));

        // When
        List<ReturnProductItemDto> result = productItemService.getProductItemsByProductId(productId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(returnProductItemDto.getId());
    }
}
