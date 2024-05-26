package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.VariationDto;
import com.spring.ecommerce.models.Variation;
import com.spring.ecommerce.models.VariationValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VariationMapper {
    VariationDto variationToVariationDto(Variation source);

    @Mapping(target = "variationName", source = "variationName")
    VariationDto variationToVariationDto(VariationValue source);

}
