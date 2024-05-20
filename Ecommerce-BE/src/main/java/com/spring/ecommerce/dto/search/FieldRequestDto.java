package com.spring.ecommerce.dto.search;

import com.spring.ecommerce.enums.FieldOperation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldRequestDto {
    private FieldOperation operator = FieldOperation.EQM;
    private String field;
    private String value;
    private String joinTable;
}
