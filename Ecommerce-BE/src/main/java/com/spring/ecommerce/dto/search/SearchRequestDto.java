package com.spring.ecommerce.dto.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDto {
    public FieldOperation operator = FieldOperation.EQ;
    private String field;
    private String value;
    private String joinTable;

    public enum FieldOperation {
        EQ,           // equals
        NE,           // not equals
        LT,           // less than
        GT,           // greater than
        LTE,          // less than or equal to
        GTE,          // greater than or equal to
        IN,           // in
        NIN,          // not in
        LIKE,         // like
        CONTAINS,     // contains
        NCONTAINS,    // not contains
        CONTAINSS,    // case-sensitive contains
        NCONTAINSS,   // case-sensitive not contains
        BETWEEN,      // between
        JOIN,         // join
        NBETWEEN,     // not between
        NULL,         // null
        NNULL,        // not null
        STARTSWITH,   // starts with
        NSTARTSWITH,  // not starts with
        STARTSWITHS,  // case-sensitive starts with
        NSTARTSWITHS, // case-sensitive not starts with
        ENDSWITH,     // ends with
        NENDSWITH,    // not ends with
        ENDSWITHS,    // case-sensitive ends with
        NENDSWITHS,   // case-sensitive not ends with
        OR,           // or
        AND           // and
    }
}
