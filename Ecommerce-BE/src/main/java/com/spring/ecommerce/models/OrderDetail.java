package com.spring.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "order_details")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class OrderDetail extends BaseEntity<Long> {

    private int quantity;
    private double price;
    private String description; // variation-combinations

    private Long userId;
    private Long orderDetailProductId;

    private String imageUrl;
    private String productName;
    private long orderDetailOrderId;

    @PrePersist
    public void prePersist() {
        this.orderDetailOrderId = this.order.getId();
    }
//    private boolean isRated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonManagedReference
    private Product product;
}
