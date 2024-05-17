package com.spring.ecommerce.data;

import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.ProductItem;
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
        var sizes = List.of("XS", "S", "M", "L", "XL", "XXL");
        var sizeValues = sizes.stream().map(size -> VariationValue.builder()
                        .variation(sizeVariations).name(size).build())
                .collect(Collectors.toList());
        sizeVariations.setVariationValues(sizeValues);
        variationRepository.save(sizeVariations);

        //Color
        Variation colorVariations = variationRepository.save(Variation.builder().name("Color").build());
        var colors = List.of(
                "Red", "Blue", "Yellow", "Green", "Orange", "Purple", "Pink",
                "Teal", "Burgundy", "Olive", "Maroon", "Light Blue",
                "Lavender", "Coral", "Cyan", "Magenta", "Turquoise",
                "Violet", "Amber", "Beige", "Brown", "Charcoal", "Crimson",
                "Fuchsia", "Gold", "Gray", "Indigo", "Ivory", "Khaki",
                "Lime", "Mint", "Navy", "Peach", "Plum", "Rose", "Ruby",
                "Salmon", "Sapphire", "Silver", "Tan", "Topaz", "Aqua",
                "Aquamarine", "Azure", "Blush", "Bronze", "Cerulean",
                "Champagne", "Copper", "Emerald", "Jade", "Lilac", "Mauve",
                "Mustard", "Periwinkle", "Sand", "Scarlet", "Sienna",
                "Sky Blue", "Slate", "Snow", "Sunset", "Thistle", "Tomato",
                "Tangerine", "Ultramarine", "Zaffre", "Amethyst", "Antique White",
                "Ash", "Bittersweet", "Cadet Blue", "Carnation", "Clover",
                "Dandelion", "Denim", "Eggplant", "Flame", "Forest Green",
                "Garnet", "Harlequin", "Honey", "Ice", "Iris", "Jasmine",
                "Kelp", "Lemon", "Licorice", "Mulberry", "Onyx", "Papaya",
                "Peridot", "Prussian Blue", "Quartz", "Raspberry", "Saffron",
                "Seashell", "Shamrock", "Smoke", "Steel", "Tawny", "Tiger's Eye",
                "Umber", "Vanilla", "Wheat", "Wisteria", "Yale Blue", "Zinnia"
        );

        var colorValues = colors.stream()
                .map(color -> VariationValue.builder()
                        .variation(colorVariations).name(color).build())
                .collect(Collectors.toList());
        colorVariations.setVariationValues(colorValues);
        variationRepository.save(colorVariations);
    }
}
