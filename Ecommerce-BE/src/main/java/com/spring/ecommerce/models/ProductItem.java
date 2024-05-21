package com.spring.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "product_items")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class ProductItem extends BaseEntity<Long> {

    private static final Logger log = LoggerFactory.getLogger(ProductItem.class);
    private double price;
    private String imageUrl;
    private int availableStock;
    private String variationCombination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Stock stock;

    @PrePersist
    protected void onCreate() {
        stock = Stock.builder()
                .availableStock(this.availableStock)
                .totalStock(this.availableStock)
                .build();
    }

    @PreUpdate
    protected void onUpdate() {
        this.availableStock = stock.getAvailableStock();
    }

    @Transient
    private Long productId;

    @PostLoad
    @PostPersist
    private void setProductId() {
        if (this.product != null) {
            this.productId = this.product.getId();
        }
    }
}
