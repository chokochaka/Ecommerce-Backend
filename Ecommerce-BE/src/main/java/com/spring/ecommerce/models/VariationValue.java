package com.spring.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "variation_values")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class VariationValue extends BaseEntity<Long> {

    private String name;

    @ManyToOne
    @JoinColumn(name = "variation_id", nullable = false)
    private Variation variation;

    @ManyToMany(mappedBy = "variationValues", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<Product> products;

    @Transient
    private String variationName;

    @PostLoad
    @PostPersist
    private void setProductId() {
        if (this.variationName != null) {
            this.variationName = this.variation.getName();
        }
    }

}
