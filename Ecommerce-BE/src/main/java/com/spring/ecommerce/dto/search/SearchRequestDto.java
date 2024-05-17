package com.spring.ecommerce.dto.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDto {
    private String column;
    private String value;

    public FieldOperation fieldOperation = FieldOperation.EQUAL;
    private String joinTable;

    public enum FieldOperation {
        EQUAL, LIKE, IN, GREATER_THAN, LESS_THAN, BETWEEN, JOIN
    }
}
