package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.VariationDto;
import com.spring.ecommerce.dto.search.PageRequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.mapper.VariationMapper;
import com.spring.ecommerce.models.Variation;
import com.spring.ecommerce.models.VariationValue;
import com.spring.ecommerce.repositories.VariationRepository;
import com.spring.ecommerce.repositories.VariationValueRepository;
import com.spring.ecommerce.services.impl.VariationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VariationServiceTests {

    @Mock
    private VariationRepository variationRepository;

    @Mock
    private VariationValueRepository variationValueRepository;

    @Mock
    private FilterSpecificationService<VariationValue> variationValueFilterSpecificationService;

    @Mock
    private VariationMapper variationMapper;

    @InjectMocks
    private VariationServiceImpl variationService;

    private Variation variation;
    private VariationValue variationValue;
    private VariationDto variationDto;
    private SearchRequestDto searchRequestDto;

    @BeforeEach
    public void setup() {
        variation = new Variation();
        variation.setId(1L);
        variation.setName("Color");

        variationValue = new VariationValue();
        variationValue.setId(1L);
        variationValue.setName("Red");
        variationValue.setVariation(variation);

        variationDto = VariationDto.builder()
                .id(1L)
                .name("Red")
                .build();

        searchRequestDto = new SearchRequestDto();
        searchRequestDto.setPageRequestDto(new PageRequestDto());
    }

    @AfterEach
    public void tearDown() {
        reset(variationRepository, variationValueRepository, variationValueFilterSpecificationService, variationMapper);
    }

    @DisplayName("JUnit test for getVariationValuesBySearch method")
    @Test
    public void getVariationValuesBySearch_ShouldReturnVariationValues() {
        // Given
        Specification<VariationValue> specification = mock(Specification.class);
        when(variationValueFilterSpecificationService.getSearchSpecification(
                searchRequestDto.getFieldRequestDtos(), searchRequestDto.getGlobalOperator()
        )).thenReturn(specification);

        when(variationValueRepository.findAll(specification)).thenReturn(Collections.singletonList(variationValue));
        when(variationMapper.variationToVariationDto(variationValue)).thenReturn(variationDto);

        // When
        List<VariationDto> result = variationService.getVariationValuesBySearch(searchRequestDto);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(variationDto.getId());
    }

    @DisplayName("JUnit test for getAllVariations method")
    @Test
    public void getAllVariations_ShouldReturnAllVariations() {
        // Given
        when(variationRepository.findAll()).thenReturn(Collections.singletonList(variation));
        when(variationMapper.variationToVariationDto(variation)).thenReturn(variationDto);

        // When
        List<VariationDto> result = variationService.getAllVariations();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(variationDto.getId());
    }

    @DisplayName("JUnit test for createVariation method")
    @Test
    public void createVariation_ShouldCreateVariation() {
        // When
        variationService.createVariation("Size");

        // Then
        ArgumentCaptor<Variation> variationArgumentCaptor = ArgumentCaptor.forClass(Variation.class);
        verify(variationRepository, times(1)).save(variationArgumentCaptor.capture());
        Variation savedVariation = variationArgumentCaptor.getValue();

        assertThat(savedVariation).isNotNull();
        assertThat(savedVariation.getName()).isEqualTo("Size");
    }

    @DisplayName("JUnit test for deleteVariation method")
    @Test
    public void deleteVariation_ShouldDeleteVariation() {
        // Given
        long variationId = 1L;

        // When
        variationService.deleteVariation(variationId);

        // Then
        verify(variationRepository, times(1)).deleteById(variationId);
    }

    @DisplayName("JUnit test for updateVariation method")
    @Test
    public void updateVariation_ShouldUpdateVariation() {
        // Given
        when(variationRepository.findById(1L)).thenReturn(Optional.of(variation));

        // When
        variationService.updateVariation(1L, "Size");

        // Then
        ArgumentCaptor<Variation> variationArgumentCaptor = ArgumentCaptor.forClass(Variation.class);
        verify(variationRepository, times(1)).save(variationArgumentCaptor.capture());
        Variation updatedVariation = variationArgumentCaptor.getValue();

        assertThat(updatedVariation).isNotNull();
        assertThat(updatedVariation.getName()).isEqualTo("Size");
    }

    @DisplayName("JUnit test for addVariationValueToVariation method")
    @Test
    public void addVariationValueToVariation_ShouldAddVariationValue() {
        // Given
        when(variationRepository.findById(1L)).thenReturn(Optional.of(variation));

        // When
        variationService.addVariationValueToVariation(1L, "Large");

        // Then
        ArgumentCaptor<VariationValue> variationValueArgumentCaptor = ArgumentCaptor.forClass(VariationValue.class);
        verify(variationValueRepository, times(1)).save(variationValueArgumentCaptor.capture());
        VariationValue savedVariationValue = variationValueArgumentCaptor.getValue();

        assertThat(savedVariationValue).isNotNull();
        assertThat(savedVariationValue.getName()).isEqualTo("Large");
        assertThat(savedVariationValue.getVariation().getId()).isEqualTo(1L);
    }

    @DisplayName("JUnit test for deleteVariationValue method")
    @Test
    public void deleteVariationValue_ShouldDeleteVariationValue() {
        // Given
        long variationValueId = 1L;

        // When
        variationService.deleteVariationValue(variationValueId);

        // Then
        verify(variationValueRepository, times(1)).deleteById(variationValueId);
    }
}
