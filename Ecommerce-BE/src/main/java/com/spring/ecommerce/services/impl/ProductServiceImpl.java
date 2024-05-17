package com.spring.ecommerce.services.impl;

import com.spring.ecommerce.repositories.ProductRepository;
import com.spring.ecommerce.services.JWTService;
import com.spring.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public void createProductWithProductItems() {

    }
}
