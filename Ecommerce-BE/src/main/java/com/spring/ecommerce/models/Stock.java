package com.spring.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stocks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Stock extends BaseEntity<Long> {
    private int totalStock; // life time stock
    private int availableStock;

    @OneToOne(fetch = FetchType.EAGER)
    private ProductItem productItem;

}
