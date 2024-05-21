package com.spring.ecommerce.data;

import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.ProductItem;
import com.spring.ecommerce.repositories.ProductItemRepository;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.repositories.StockRepository;
import com.spring.ecommerce.repositories.VariationRepository;
import com.spring.ecommerce.repositories.VariationValueRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductInit {
    private final ProductRepository productRepository;
    private final VariationRepository variationRepository;
    private final VariationValueRepository variationValueRepository;

    private final ProductItemRepository productItemRepository;
    private final StockRepository stockRepository;

    @PostConstruct
    public void initData() {
        createProduct();
    }

    void createProduct() {

        String[] variationCombinations = {"XXS:yellow", "XS:green", "S:blue", "M:red", "L:black", "XL:white"};

        for (int i = 0; i < 10; i++) {
            String variationCombination = variationCombinations[i % variationCombinations.length];

            Product product = productRepository.save(Product.builder()
                    .productItems(List.of())
                    .name("Product " + (i + 1))
                    .description("Product " + (i + 1) + " description")
                    .isFeatured(i % 2 == 0) // Alternate between true and false for isFeatured
                    .averageRating(4.0 + i * 0.1) // Increment average rating
                    .build());

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
