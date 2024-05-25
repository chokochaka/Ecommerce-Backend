package com.spring.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "variations")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class Variation extends BaseEntity<Long> {

    private String name;


    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VariationValue> variationValues;

}
