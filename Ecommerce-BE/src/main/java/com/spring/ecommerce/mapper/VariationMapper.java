package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.VariationDto;
import com.spring.ecommerce.models.Variation;
import com.spring.ecommerce.models.VariationValue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VariationMapper {
    VariationDto variationToVariationDto(Variation source);

    VariationDto variationToVariationDto(VariationValue source);

}
