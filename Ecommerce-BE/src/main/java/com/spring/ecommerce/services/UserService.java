package com.spring.ecommerce.services;

import com.spring.ecommerce.dto.product.ProductDto;
import com.spring.ecommerce.dto.search.SearchRequestDto;
import com.spring.ecommerce.dto.user.ReturnUserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<ReturnUserDto> getUsersBySearch(SearchRequestDto searchRequestDto);

    Page<ReturnUserDto> getUsersBySearchAndPagination(SearchRequestDto searchRequestDto);

    void updateUser(long id, ProductDto productDto);

    void deleteUser(long id);
}
