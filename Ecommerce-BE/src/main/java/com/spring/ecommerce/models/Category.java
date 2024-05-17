//package com.spring.ecommerce.models;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import jakarta.persistence.UniqueConstraint;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//@Entity
//@Table(name = "categories",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = "name"),
//        })
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Getter
//@Setter
//@Builder
//public class Category extends BaseEntity<Long> {
//
//    private String name;
//
//    private String description;
//
//    @OneToOne(mappedBy = "category", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private Product product;
//
//    @ManyToOne
//    @JoinColumn(name = "parent_category_id")
//    private Category parentCategory;
//}
