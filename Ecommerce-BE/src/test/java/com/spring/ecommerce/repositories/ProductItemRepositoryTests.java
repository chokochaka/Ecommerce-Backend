package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.ProductItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductItemRepositoryTests {

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductRepository productRepository;

    private Product product;
    private ProductItem productItem1;
    private ProductItem productItem2;

    @BeforeEach
    public void setup() {
        // Setup product
        product = Product.builder()
                .name("Test Product")
                .description("Test Product Description")
                .build();
        product = productRepository.save(product);

        // Setup product items
        productItem1 = ProductItem.builder()
                .product(product)
                .build();
        productItem2 = ProductItem.builder()
                .product(product)
                .build();
        productItemRepository.save(productItem1);
        productItemRepository.save(productItem2);
    }

    @AfterEach
    public void tearDown() {
        productItemRepository.deleteAll();
        productRepository.deleteAll();
    }

    @DisplayName("JUnit test for findByProductId when product ID exists")
    @Test
    public void findByProductId_WhenProductIdExists_ReturnsProductItems() {
        // When
        List<ProductItem> productItems = productItemRepository.findByProductId(product.getId());

        // Then
        assertThat(productItems).isNotEmpty();
        assertThat(productItems).hasSize(2);
        assertThat(productItems).contains(productItem1, productItem2);
    }

    @DisplayName("JUnit test for findByProductId when product ID does not exist")
    @Test
    public void findByProductId_WhenProductIdDoesNotExist_ReturnsEmptyList() {
        // When
        List<ProductItem> productItems = productItemRepository.findByProductId(-1L); // Using an ID that does not exist

        // Then
        assertThat(productItems).isEmpty();
    }
}
