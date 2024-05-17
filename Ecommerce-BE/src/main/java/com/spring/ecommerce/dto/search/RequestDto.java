package com.spring.ecommerce.dto.search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDto {
    private List<SearchRequestDto> listSearchRequestDto;
    public GlobalOperator globalOperator;

    public enum GlobalOperator {
        AND, OR, ALL
    }
}
