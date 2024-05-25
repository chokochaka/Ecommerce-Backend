package com.spring.ecommerce.data;

import com.spring.ecommerce.models.Variation;
import com.spring.ecommerce.models.VariationValue;
import com.spring.ecommerce.repositories.ProductItemRepository;
import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.repositories.StockRepository;
import com.spring.ecommerce.repositories.VariationRepository;
import com.spring.ecommerce.repositories.VariationValueRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VariationInit {
    private final ProductRepository productRepository;
    private final VariationRepository variationRepository;
    private final VariationValueRepository variationValueRepository;

    private final ProductItemRepository productItemRepository;
    private final StockRepository stockRepository;

    @PostConstruct
    public void initData() {
        createVariationAndVariationValues();
    }


    void createVariationAndVariationValues() {
        // Size
        Variation sizeVariations = variationRepository.save(Variation.builder().name("Size").build());
        var sizes = List.of("XS", "S", "M", "L", "XL");
        var sizeValues = sizes.stream().map(size -> VariationValue.builder()
                        .variation(sizeVariations).name(size).build())
                .collect(Collectors.toList());
        sizeVariations.setVariationValues(sizeValues);
        variationRepository.save(sizeVariations);

        //Color
        Variation colorVariations = variationRepository.save(Variation.builder().name("Color").build());
        var colors = List.of(
                "Trắng", "Đen", "Hồng Nhạt", "Cam Nhạt", "Da Đậm", "Nâu", "Xanh Lá", "Tím Nhạt", "Xám", "Đỏ"
        );

        var colorValues = colors.stream()
                .map(color -> VariationValue.builder()
                        .variation(colorVariations).name(color).build())
                .collect(Collectors.toList());
        colorVariations.setVariationValues(colorValues);
        variationRepository.save(colorVariations);
    }
}
