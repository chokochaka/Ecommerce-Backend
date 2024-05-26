package com.spring.ecommerce.repositories;

import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private Category category;
    private Product product;

    @BeforeEach
    public void setup() {
        // Setup category
        category = Category.builder()
                .name("Category1")
                .description("Category for Category1 products")
                .build();
        // setup product
        product = Product.builder()
                .name("Product1")
                .description("Product1 description")
                .categories(Set.of(category))
                .build();

        category = categoryRepository.save(category);
        product = productRepository.save(product);
    }

    @DisplayName("JUnit test for finding category by name")
    @Test
    public void findCategoryByName_WhenCategoryExists_ReturnsCategory() {
        // when
        Category foundCategory = categoryRepository.findCategoryByName(category.getName());

        // then
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo(category.getName());
    }

    @DisplayName("JUnit test for finding category by name when category does not exist")
    @Test
    public void findCategoryByName_WhenCategoryDoesNotExist_ReturnsNull() {
        // when
        Category foundCategory = categoryRepository.findCategoryByName("NonExistent");

        // then
        assertThat(foundCategory).isNull();
    }

    @DisplayName("JUnit test for deleting product categories by category ID")
    @Test
    @Transactional
    public void deleteProductCategoriesByCategoryId_WhenCategoryExists() {
        // When
        categoryRepository.deleteProductCategoriesByCategoryId(category.getId());
        categoryRepository.deleteById(category.getId());

        // Then
        Optional<Category> foundCategory = categoryRepository.findById(category.getId());
        assertThat(foundCategory).isEmpty();
    }
}
