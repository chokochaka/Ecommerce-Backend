package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.VariationDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.dto.user.ReturnUserDto;
import com.spring.ecommerce.models.Variation;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VariationService {

    List<VariationDto> getVariationValuesBySearch(SearchRequestDto searchRequestDto);

    Page<VariationDto> getVariationValuesBySearchAndPagination(SearchRequestDto searchRequestDto);

    List<VariationDto> getAllVariations();

    void createVariation(String variationName);

    void deleteVariation(Long variationId);

    void updateVariation(Long variationId, String variationName);

    void addVariationValueToVariation(Long variationId, String variationValue);

    void deleteVariationValue(Long variationValueId);
}
