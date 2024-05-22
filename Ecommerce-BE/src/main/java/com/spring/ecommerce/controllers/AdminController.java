package com.spring.ecommerce.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: test purpose
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
