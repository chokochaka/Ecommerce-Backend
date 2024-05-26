package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.VariationDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.VariationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public List<VariationDto> getAllVariations() {
        return variationService.getAllVariations();
    }

    @PostMapping("/variation")
    public void createVariation(@RequestBody String variationName) {
        variationService.createVariation(variationName);
    }

    @PutMapping("/variation/{variationId}")
    public void updateVariation(@PathVariable Long variationId, @RequestBody String variationName) {
        variationService.updateVariation(variationId, variationName);
    }

    @DeleteMapping("/variation/{variationId}")
    public void deleteVariation(@PathVariable Long variationId) {
        variationService.deleteVariation(variationId);
    }


    @PostMapping("/search")
    public List<VariationDto> getVariationValuesBySearch(@RequestBody SearchRequestDto searchRequestDto) {
        return variationService.getVariationValuesBySearch(searchRequestDto);
    }

    @PostMapping("/search/paginated")
    public Page<VariationDto> getVariationValuesBySearchAndPagination(@RequestBody SearchRequestDto searchRequestDto) {
        return variationService.getVariationValuesBySearchAndPagination(searchRequestDto);
    }


    @PostMapping("/{variationId}")
    public void addVariationValueToVariation(@PathVariable Long variationId, @RequestBody String variationValue) {
        variationService.addVariationValueToVariation(variationId, variationValue);
    }

    @DeleteMapping("/{variationValueId}")
    public void deleteVariationValue(@PathVariable Long variationValueId) {
        variationService.deleteVariationValue(variationValueId);
    }
}
