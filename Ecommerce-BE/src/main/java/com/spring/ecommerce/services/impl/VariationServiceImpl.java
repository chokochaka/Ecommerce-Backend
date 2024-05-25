package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.VariationDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.VariationMapper;
import com.spring.ecommerce.models.Product;
import com.spring.ecommerce.models.Variation;
import com.spring.ecommerce.models.VariationValue;
import com.spring.ecommerce.repositories.VariationRepository;
import com.spring.ecommerce.repositories.VariationValueRepository;
import com.spring.ecommerce.services.FilterSpecificationService;
import com.spring.ecommerce.services.VariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VariationServiceImpl implements VariationService {
    private final VariationRepository variationRepository;
    private final VariationValueRepository variationValueRepository;
    private final FilterSpecificationService<VariationValue> variationValueFilterSpecificationService;
    private final VariationMapper variationMapper;


    @Override
    public List<VariationDto> getVariationValuesBySearch(SearchRequestDto searchRequestDto) {
        Specification<VariationValue> variationValueSearchSpecification = variationValueFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        return variationValueRepository.findAll(variationValueSearchSpecification).stream()
                .map(variationMapper::variationToVariationDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<VariationDto> getVariationValuesBySearchAndPagination(SearchRequestDto searchRequestDto) {
        Specification<VariationValue> variationValueSearchSpecification = variationValueFilterSpecificationService
                .getSearchSpecification(
                        searchRequestDto.getFieldRequestDtos()
                        , searchRequestDto.getGlobalOperator()
                );
        Pageable pageable = new PageRequestDto().getPageable(searchRequestDto.getPageRequestDto());
        Page<VariationValue> productPage = variationValueRepository.findAll(variationValueSearchSpecification, pageable);
        return productPage.map(variationMapper::variationToVariationDto);
    }

    @Override
    public List<VariationDto> getAllVariations() {
        List<Variation> variations = variationRepository.findAll();
        return variations.stream()
                .map(variationMapper::variationToVariationDto)
                .collect(Collectors.toList());
    }


    @Override
    public void createVariation(String variationName) {
        Variation variation = new Variation();
        variation.setName(variationName);
        variationRepository.save(variation);
    }

    @Override
    public void deleteVariation(Long variationId) {
        variationRepository.deleteById(variationId);
    }

    @Override
    public void updateVariation(Long variationId, String variationName) {
        Variation variation = variationRepository.findById(variationId).orElseThrow();
        variation.setName(variationName);
        variationRepository.save(variation);
    }

    @Override
    public void addVariationValueToVariation(Long variationId, String variationValue) {
        Variation variation = variationRepository.findById(variationId).orElseThrow();
        VariationValue variationValueObj = new VariationValue();
        variationValueObj.setVariation(variation);
        variationRepository.save(variation);
    }

    @Override
    public void deleteVariationValue(Long variationValueId) {
        variationValueRepository.deleteById(variationValueId);
    }
}
