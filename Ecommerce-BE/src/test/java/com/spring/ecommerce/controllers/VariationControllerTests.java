package com.spring.ecommerce.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.dto.VariationDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.services.VariationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = VariationController.class)
public class VariationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VariationService variationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllVariations_ShouldReturnListOfVariations() throws Exception {
        // given
        VariationDto variationDto = VariationDto.builder()
                .id(1L)
                .name("Size")
                .build();
        List<VariationDto> variationDtoList = Collections.singletonList(variationDto);
        when(variationService.getAllVariations()).thenReturn(variationDtoList);

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/variationValue/variation")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(variationDtoList.size())))
                .andExpect(jsonPath("$[0].name", is(variationDto.getName())));
    }

    @Test
    public void createVariation_ShouldSaveVariation() throws Exception {
        // given
        String variationName = "Size";
        doNothing().when(variationService).createVariation(anyString());

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/variationValue/variation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(variationName)));

        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateVariation_ShouldUpdateVariation() throws Exception {
        // given
        Long variationId = 1L;
        String variationName = "Updated Size";
        doNothing().when(variationService).updateVariation(anyLong(), anyString());

        // when
        ResultActions response = mockMvc.perform(put("/api/v1/variationValue/variation/{variationId}", variationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(variationName)));

        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteVariation_ShouldDeleteVariation() throws Exception {
        // given
        Long variationId = 1L;
        doNothing().when(variationService).deleteVariation(anyLong());

        // when
        ResultActions response = mockMvc.perform(delete("/api/v1/variationValue/variation/{variationId}", variationId));

        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getVariationValuesBySearch_ShouldReturnListOfVariationValues() throws Exception {
        // given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        VariationDto variationDto = VariationDto.builder()
                .id(1L)
                .name("Size")
                .build();
        List<VariationDto> variationDtoList = Collections.singletonList(variationDto);
        when(variationService.getVariationValuesBySearch(any(SearchRequestDto.class))).thenReturn(variationDtoList);

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/variationValue/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(variationDtoList.size())))
                .andExpect(jsonPath("$[0].name", is(variationDto.getName())));
    }

    @Test
    public void getVariationValuesBySearchAndPagination_ShouldReturnPagedVariationValues() throws Exception {
        // given
        SearchRequestDto searchRequestDto = new SearchRequestDto();
        VariationDto variationDto = VariationDto.builder()
                .id(1L)
                .name("Size")
                .build();
        Page<VariationDto> variationDtoPage = new PageImpl<>(Collections.singletonList(variationDto));
        when(variationService.getVariationValuesBySearchAndPagination(any(SearchRequestDto.class))).thenReturn(variationDtoPage);

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/variationValue/search/paginated")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequestDto)));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is((int) variationDtoPage.getTotalElements())))
                .andExpect(jsonPath("$.content[0].name", is(variationDto.getName())));
    }

    @Test
    public void addVariationValueToVariation_ShouldAddVariationValue() throws Exception {
        // given
        Long variationId = 1L;
        String variationValue = "Large";
        doNothing().when(variationService).addVariationValueToVariation(anyLong(), anyString());

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/variationValue/{variationId}", variationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(variationValue)));

        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteVariationValue_ShouldDeleteVariationValue() throws Exception {
        // given
        Long variationValueId = 1L;
        doNothing().when(variationService).deleteVariationValue(anyLong());

        // when
        ResultActions response = mockMvc.perform(delete("/api/v1/variationValue/{variationValueId}", variationValueId));

        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }
}
