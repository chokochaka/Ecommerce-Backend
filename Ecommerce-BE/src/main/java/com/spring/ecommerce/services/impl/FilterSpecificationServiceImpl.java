package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.dto.search.RequestDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
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
            List<SearchRequestDto> listSearchRequestDto,
            RequestDto.GlobalOperator globalOperator
    ) {
        return (root, query, criteriaBuilder) -> {
            if (globalOperator == RequestDto.GlobalOperator.ALL) {
                return criteriaBuilder.conjunction(); // return all
            }

            List<Predicate> predicates = new ArrayList<>();

            for (SearchRequestDto searchRequestDto : listSearchRequestDto) {
                switch (searchRequestDto.getOperator()) {
                    case EQ:
                        Predicate equal = criteriaBuilder.equal(root.get(searchRequestDto.getField()), searchRequestDto.getValue());
                        predicates.add(equal);
                        break;

                    case LIKE:
                        Predicate like = criteriaBuilder.like(root.get(searchRequestDto.getField()), "%" + searchRequestDto.getValue() + "%");
                        predicates.add(like);
                        break;

                    case IN:
                        // "1,2,3"
                        String[] split = searchRequestDto.getValue().split(",");
                        Predicate in = root.get(searchRequestDto.getField()).in(Arrays.asList(split));
                        predicates.add(in);
                        break;

                    case GT:
                        Predicate greaterThan = criteriaBuilder.greaterThan(root.get(searchRequestDto.getField()), searchRequestDto.getValue());
                        predicates.add(greaterThan);
                        break;

                    case LT:
                        Predicate lessThan = criteriaBuilder.lessThan(root.get(searchRequestDto.getField()), searchRequestDto.getValue());
                        predicates.add(lessThan);
                        break;

                    case BETWEEN:
                        //"10, 20"
                        String[] split1 = searchRequestDto.getValue().split(",");
                        Predicate between = criteriaBuilder.between(root.get(searchRequestDto.getField()), Long.parseLong(split1[0]), Long.parseLong(split1[1]));
                        predicates.add(between);
                        break;

                    case JOIN:
                        Predicate join = criteriaBuilder.equal(root.join(searchRequestDto.getJoinTable()).get(searchRequestDto.getField()), searchRequestDto.getValue());
                        predicates.add(join);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: ");
                }
            }
            if (globalOperator == RequestDto.GlobalOperator.AND) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } else { // OR
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
