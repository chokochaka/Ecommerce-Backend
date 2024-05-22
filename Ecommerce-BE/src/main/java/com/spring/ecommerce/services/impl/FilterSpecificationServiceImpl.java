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
            // TODO: Fix enum name
            for (FieldRequestDto fieldRequestDto : fieldRequestDtos) {
                switch (fieldRequestDto.getOperator()) {
                    case EQM:
                        Predicate equal = criteriaBuilder.equal(root.get(fieldRequestDto.getField()), fieldRequestDto.getValue());
                        predicates.add(equal);
                        break;

                    case CONTAINS:
                        Predicate like = criteriaBuilder.like(root.get(fieldRequestDto.getField()), "%" + fieldRequestDto.getValue() + "%");
                        predicates.add(like);
                        break;

                    case INM:
                        // 1,2,3
                        String[] split = fieldRequestDto.getValue().split(",");
                        Predicate in = root.get(fieldRequestDto.getField()).in(Arrays.asList(split));
                        predicates.add(in);
                        break;

                    case GT:
                        Predicate greaterThan = criteriaBuilder.greaterThan(root.get(fieldRequestDto.getField()), fieldRequestDto.getValue());
                        predicates.add(greaterThan);
                        break;

                    case LT:
                        Predicate lessThan = criteriaBuilder.lessThan(root.get(fieldRequestDto.getField()), fieldRequestDto.getValue());
                        predicates.add(lessThan);
                        break;

                    case BETWEEN:
                        //"10, 20"
                        String[] split1 = fieldRequestDto.getValue().split(",");
                        Predicate between = criteriaBuilder.between(root.get(fieldRequestDto.getField()), Long.parseLong(split1[0]), Long.parseLong(split1[1]));
                        predicates.add(between);
                        break;

                    case STARTSWITH:
                        Predicate startsWith = criteriaBuilder.like(root.get(fieldRequestDto.getField()), fieldRequestDto.getValue() + "%");
                        predicates.add(startsWith);
                        break;

                    case ENDSWITH:
                        Predicate endsWith = criteriaBuilder.like(root.get(fieldRequestDto.getField()), "%" + fieldRequestDto.getValue());
                        predicates.add(endsWith);
                        break;

                    case JOIN:
                        Predicate join = criteriaBuilder.equal(root.join(fieldRequestDto.getJoinTable()).get(fieldRequestDto.getField()), fieldRequestDto.getValue());
                        predicates.add(join);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: ");
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
