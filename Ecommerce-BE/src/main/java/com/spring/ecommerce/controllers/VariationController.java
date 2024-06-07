package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.VariationDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.VariationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/variationValue")
@RequiredArgsConstructor
@Tag(name = "Variation", description = "Variation API")
public class VariationController {

    private final VariationService variationService;

    @GetMapping("/variation")
    public ResponseEntity<List<VariationDto>> getAllVariations() {
        List<VariationDto> variations = variationService.getAllVariations();
        return new ResponseEntity<>(variations, HttpStatus.OK);
    }

    @PostMapping("/variation")
    public ResponseEntity<Void> createVariation(@Valid @RequestBody String variationName) {
        variationService.createVariation(variationName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/variation/{variationId}")
    public ResponseEntity<Void> updateVariation(@PathVariable Long variationId, @Valid @RequestBody String variationName) {
        variationService.updateVariation(variationId, variationName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/variation/{variationId}")
    public ResponseEntity<Void> deleteVariation(@PathVariable Long variationId) {
        variationService.deleteVariation(variationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/search")
    public ResponseEntity<List<VariationDto>> getVariationValuesBySearch(@Valid @RequestBody SearchRequestDto searchRequestDto) {
        List<VariationDto> variations = variationService.getVariationValuesBySearch(searchRequestDto);
        return new ResponseEntity<>(variations, HttpStatus.OK);
    }

    @PostMapping("/search/paginated")
    public ResponseEntity<Page<VariationDto>> getVariationValuesBySearchAndPagination(@Valid @RequestBody SearchRequestDto searchRequestDto) {
        Page<VariationDto> variations = variationService.getVariationValuesBySearchAndPagination(searchRequestDto);
        return new ResponseEntity<>(variations, HttpStatus.OK);
    }

    @PostMapping("/{variationId}")
    public ResponseEntity<Void> addVariationValueToVariation(@PathVariable Long variationId, @Valid @RequestBody String variationValue) {
        variationService.addVariationValueToVariation(variationId, variationValue);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{variationValueId}")
    public ResponseEntity<Void> deleteVariationValue(@PathVariable Long variationValueId) {
        variationService.deleteVariationValue(variationValueId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
