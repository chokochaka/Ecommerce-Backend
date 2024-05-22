package com.spring.ecommerce.dto.search;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@Setter
public class PageRequestDto {

    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private Sort.Direction sort = Sort.Direction.ASC;

    private String sortByColumn = "id";

    public Pageable getPageable(PageRequestDto dto) {
        Integer page = Objects.nonNull(dto.getPageNo()) ? dto.getPageNo() : this.pageNo;
        Integer size = Objects.nonNull(dto.getPageSize()) ? dto.getPageSize() : this.pageSize;
        Sort.Direction sort = Objects.nonNull(dto.getSort()) ? dto.getSort() : Sort.Direction.ASC;
        String sortByColumn = Objects.nonNull(dto.getSortByColumn()) ? dto.getSortByColumn() : this.sortByColumn;

        if (page <= 0) {
            throw new IllegalArgumentException("Page number must be greater than 0");
        }
        page = page - 1; // Page number starts from 0 but client will send 1
        return PageRequest.of(page, size, sort, sortByColumn);
    }

}
