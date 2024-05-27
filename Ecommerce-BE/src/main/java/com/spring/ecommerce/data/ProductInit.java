package com.spring.ecommerce.data;

import com.spring.ecommerce.models.Category;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.ProductItem;
import com.spring.ecommerce.repositories.CategoryRepository;
import com.spring.ecommerce.repositories.ProductItemRepository;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.repositories.StockRepository;
import com.spring.ecommerce.repositories.VariationRepository;
import com.spring.ecommerce.repositories.VariationValueRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProductInit {
    private static final Logger log = LoggerFactory.getLogger(ProductInit.class);
    private final ProductRepository productRepository;
    private final VariationRepository variationRepository;
    private final VariationValueRepository variationValueRepository;

    private final ProductItemRepository productItemRepository;
    private final CategoryRepository categoryRepository;
    private final StockRepository stockRepository;
    private static final Random random = new Random();

    public static List<Integer> generateUniqueRandomNumbers(int count, int min, int max) {
        if (count > (max - min + 1)) {
            throw new IllegalArgumentException("Count is larger than the range of unique numbers available.");
        }

        Random random = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();

        while (uniqueNumbers.size() < count) {
            int number = random.nextInt(max - min + 1) + min;
            uniqueNumbers.add(number);
        }

        return new ArrayList<>(uniqueNumbers);
    }

    @PostConstruct
    public void initData() {
        createProduct();
    }

    void createProduct() {

        String[] variationCombinations = {"XS:Trắng", "S:Hồng Nhạt", "M:Nâu", "L:Tím Nhạt", "XL:Đỏ"};

        for (int i = 0; i < 10; i++) {
            String variationCombination = variationCombinations[i % variationCombinations.length];
            Set<Category> categories = new HashSet<>();
            for (int numericalValue : generateUniqueRandomNumbers(10, 1, 10)) {
                Category c = categoryRepository.findById((long) numericalValue).orElseThrow();
                categories.add(c);
            }
//            String[] parts = variationCombination.split(":");
//            VariationValue size = variationValueRepository.findByName(parts[0]);
//
//            VariationValue color = variationValueRepository.findByName(parts[1]);

            Product product = productRepository.save(Product.builder()
                    .productItems(List.of())
                    .name("Product " + (i + 1))
                    .description("Product " + (i + 1) + " description")
                    .isFeatured(i % 2 == 0) // Alternate between true and false for isFeatured
                    .averageRating(4.0 + i * 0.1) // Increment average rating
                    .categories(categories)
                    .build());
//            size.setProducts((Set.of(product)));
//            color.setProducts((Set.of(product)));
//            product.setVariationValues(Set.of(size, color));

            productItemRepository.saveAll(List.of(
                    ProductItem.builder()
                            .product(product)
                            .availableStock(10)
                            .price(100.0 + i * 10) // Increment price
                            .variationCombination(variationCombination)
                            .build(),
                    ProductItem.builder()
                            .product(product)
                            .availableStock(10)
                            .price(200.0 + i * 10) // Increment price
                            .variationCombination(variationCombination)
                            .build(),
                    ProductItem.builder()
                            .product(product)
                            .availableStock(10)
                            .price(300.0 + i * 10) // Increment price
                            .variationCombination(variationCombination)
                            .build()
            ));
        }
    }
}
