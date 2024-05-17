package com.spring.ecommerce.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductItem extends BaseEntity<Long> {

    private String image_url;
    private String variationCombination;
    private int available_stock;
    private double price;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToOne(mappedBy = "productItem", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Stock stock;
}
