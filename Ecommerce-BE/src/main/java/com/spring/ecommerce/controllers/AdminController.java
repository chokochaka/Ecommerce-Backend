package com.spring.ecommerce.controllers;

import com.spring.ecommerce.dto.auth.SignUpDto;
import com.spring.ecommerce.dto.auth.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/user")
    public String user() {
        return "user role";
    }

    @GetMapping("/iv")
    public String iv() {
        return "inventory role";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin role";
    }

    @GetMapping("/all")
    public String all() {
        return "accept all access";
    }
}
