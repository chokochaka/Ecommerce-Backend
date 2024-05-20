package com.spring.ecommerce.enums;

// EQM, INM cause MUI value is not supported EQ, IN
public enum FieldOperation {
    EQM,           // equals
    NE,           // not equals
    LT,           // less than
    GT,           // greater than
    LTE,          // less than or equal to
    GTE,          // greater than or equal to
    INM,           // in
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
}
