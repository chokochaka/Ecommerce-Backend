package com.spring.ecommerce.dto.search;

import com.spring.ecommerce.enums.GlobalOperator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequestDto {
    private GlobalOperator globalOperator;
    private List<FieldRequestDto> fieldRequestDtos;
    private PageRequestDto pageRequestDto;

}
