package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.search.FieldRequestDto;
import com.spring.ecommerce.enums.GlobalOperator;
import com.spring.ecommerce.services.FilterSpecificationService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FilterSpecificationServiceImpl<T> implements FilterSpecificationService<T> {
    @Override
    public Specification<T> getSearchSpecification(
            List<FieldRequestDto> fieldRequestDtos,
            GlobalOperator globalOperator
    ) {
        return (root, query, criteriaBuilder) -> {
            if (globalOperator == GlobalOperator.ALL) {
                return criteriaBuilder.conjunction(); // return all
            }

            List<Predicate> predicates = new ArrayList<>();
            for (FieldRequestDto fieldRequestDto : fieldRequestDtos) {
                switch (fieldRequestDto.getOperator()) {
                    case EQM: // equal string
                        if (Objects.equals(fieldRequestDto.getField(), "id")) {
                            Predicate equal = criteriaBuilder.equal(root.get(fieldRequestDto.getField()), fieldRequestDto.getValue());
                            predicates.add(equal);
                            break;
                        }

                        if ("true".equalsIgnoreCase(fieldRequestDto.getValue()) || "false".equalsIgnoreCase(fieldRequestDto.getValue())) {
                            boolean booleanValue = Boolean.parseBoolean(fieldRequestDto.getValue());
                            Predicate equal = criteriaBuilder.equal(root.get(fieldRequestDto.getField()), booleanValue);
                            predicates.add(equal);
                        } else {
                            Predicate equal = criteriaBuilder.equal(
                                    criteriaBuilder.lower(root.get(fieldRequestDto.getField())),
                                    fieldRequestDto.getValue().toLowerCase()
                            );
                            predicates.add(equal);
                        }
                        break;

                    case CONTAINS: // contains string
                        Predicate like = criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(fieldRequestDto.getField())),
                                "%" + fieldRequestDto.getValue().toLowerCase() + "%"
                        );
                        predicates.add(like);
                        break;

                    case INM: // in string
                        // 1,2,3
                        String[] split = fieldRequestDto.getValue().split(",");
                        List<String> lowerCaseValues = Arrays.stream(split)
                                .map(String::toLowerCase)
                                .toList();
                        Predicate in = criteriaBuilder.lower(root.get(fieldRequestDto.getField())).in(lowerCaseValues);
                        predicates.add(in);
                        break;

                    case GT: // greater than
                        Predicate greaterThan = criteriaBuilder.greaterThan(root.get(fieldRequestDto.getField()), fieldRequestDto.getValue());
                        predicates.add(greaterThan);
                        break;

                    case LT: // less than
                        Predicate lessThan = criteriaBuilder.lessThan(root.get(fieldRequestDto.getField()), fieldRequestDto.getValue());
                        predicates.add(lessThan);
                        break;

                    case BETWEEN: // between 2 values
                        //"10,20"
                        String[] split1 = fieldRequestDto.getValue().split(",");
                        Predicate between = criteriaBuilder.between(root.get(fieldRequestDto.getField()), Double.parseDouble(split1[0]), Double.parseDouble(split1[1]));
                        predicates.add(between);
                        break;

                    case STARTSWITH: // starts with string
                        Predicate startsWith = criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(fieldRequestDto.getField())),
                                fieldRequestDto.getValue().toLowerCase() + "%"
                        );
                        predicates.add(startsWith);
                        break;

                    case ENDSWITH: // ends with string
                        Predicate endsWith = criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(fieldRequestDto.getField())),
                                "%" + fieldRequestDto.getValue().toLowerCase()
                        );
                        predicates.add(endsWith);
                        break;

                    case JOIN: // join 2 tables
                        Predicate join = criteriaBuilder.equal(root.join(fieldRequestDto.getJoinTable()).get(fieldRequestDto.getField()), fieldRequestDto.getValue());
                        predicates.add(join);
                        break;

                    case NE: // not equals
                        Predicate notEqual = criteriaBuilder.notEqual(
                                criteriaBuilder.lower(root.get(fieldRequestDto.getField())),
                                fieldRequestDto.getValue().toLowerCase()
                        );
                        predicates.add(notEqual);
                        break;

                    case LTE: // less than or equal to
                        Predicate lessThanOrEqual = criteriaBuilder.lessThanOrEqualTo(root.get(fieldRequestDto.getField()), fieldRequestDto.getValue());
                        predicates.add(lessThanOrEqual);
                        break;

                    case GTE: // greater than or equal to
                        Predicate greaterThanOrEqual = criteriaBuilder.greaterThanOrEqualTo(root.get(fieldRequestDto.getField()), fieldRequestDto.getValue());
                        predicates.add(greaterThanOrEqual);
                        break;

                    case NIN: // not in
                        String[] splitNin = fieldRequestDto.getValue().split(",");
                        List<String> lowerCaseValuesNin = Arrays.stream(splitNin)
                                .map(String::toLowerCase)
                                .toList();
                        Predicate notIn = criteriaBuilder.lower(root.get(fieldRequestDto.getField())).in(lowerCaseValuesNin).not();
                        predicates.add(notIn);
                        break;

                    case NCONTAINS: // not contains
                        Predicate notContains = criteriaBuilder.notLike(
                                criteriaBuilder.lower(root.get(fieldRequestDto.getField())),
                                "%" + fieldRequestDto.getValue().toLowerCase() + "%"
                        );
                        predicates.add(notContains);
                        break;

                    case CONTAINSS: // case-sensitive contains
                        Predicate caseSensitiveContains = criteriaBuilder.like(
                                root.get(fieldRequestDto.getField()),
                                "%" + fieldRequestDto.getValue() + "%"
                        );
                        predicates.add(caseSensitiveContains);
                        break;

                    case NCONTAINSS: // case-sensitive not contains
                        Predicate caseSensitiveNotContains = criteriaBuilder.notLike(
                                root.get(fieldRequestDto.getField()),
                                "%" + fieldRequestDto.getValue() + "%"
                        );
                        predicates.add(caseSensitiveNotContains);
                        break;

                    case NBETWEEN: // not between
                        String[] splitNBetween = fieldRequestDto.getValue().split(",");
                        Predicate notBetween = criteriaBuilder.not(
                                criteriaBuilder.between(root.get(fieldRequestDto.getField()), Long.parseLong(splitNBetween[0]), Long.parseLong(splitNBetween[1]))
                        );
                        predicates.add(notBetween);
                        break;

                    case NULL: // is null
                        Predicate isNull = criteriaBuilder.isNull(root.get(fieldRequestDto.getField()));
                        predicates.add(isNull);
                        break;

                    case NNULL: // is not null
                        Predicate isNotNull = criteriaBuilder.isNotNull(root.get(fieldRequestDto.getField()));
                        predicates.add(isNotNull);
                        break;

                    case NSTARTSWITH: // not starts with string
                        Predicate notStartsWith = criteriaBuilder.notLike(
                                criteriaBuilder.lower(root.get(fieldRequestDto.getField())),
                                fieldRequestDto.getValue().toLowerCase() + "%"
                        );
                        predicates.add(notStartsWith);
                        break;

                    case STARTSWITHS: // case-sensitive starts with string
                        Predicate caseSensitiveStartsWith = criteriaBuilder.like(
                                root.get(fieldRequestDto.getField()),
                                fieldRequestDto.getValue() + "%"
                        );
                        predicates.add(caseSensitiveStartsWith);
                        break;

                    case NSTARTSWITHS: // case-sensitive not starts with string
                        Predicate caseSensitiveNotStartsWith = criteriaBuilder.notLike(
                                root.get(fieldRequestDto.getField()),
                                fieldRequestDto.getValue() + "%"
                        );
                        predicates.add(caseSensitiveNotStartsWith);
                        break;

                    case NENDSWITH: // not ends with string
                        Predicate notEndsWith = criteriaBuilder.notLike(
                                criteriaBuilder.lower(root.get(fieldRequestDto.getField())),
                                "%" + fieldRequestDto.getValue().toLowerCase()
                        );
                        predicates.add(notEndsWith);
                        break;

                    case ENDSWITHS: // case-sensitive ends with string
                        Predicate caseSensitiveEndsWith = criteriaBuilder.like(
                                root.get(fieldRequestDto.getField()),
                                "%" + fieldRequestDto.getValue()
                        );
                        predicates.add(caseSensitiveEndsWith);
                        break;

                    case NENDSWITHS: // case-sensitive not ends with string
                        Predicate caseSensitiveNotEndsWith = criteriaBuilder.notLike(
                                root.get(fieldRequestDto.getField()),
                                "%" + fieldRequestDto.getValue()
                        );
                        predicates.add(caseSensitiveNotEndsWith);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + fieldRequestDto.getOperator());
                }
            }
            if (globalOperator == GlobalOperator.AND) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } else { // OR
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
