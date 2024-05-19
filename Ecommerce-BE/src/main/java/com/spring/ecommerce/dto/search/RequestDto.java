package com.spring.ecommerce.dto.search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDto {
    public GlobalOperator globalOperator;
    private List<SearchRequestDto> searchRequestDtos;
    private PageRequestDto pageRequestDto;

    public enum GlobalOperator {
        AND, OR, ALL
    }
}
