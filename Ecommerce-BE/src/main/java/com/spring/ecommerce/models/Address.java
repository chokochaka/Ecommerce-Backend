package com.spring.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "addresses")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class Address extends BaseEntity<Long> {

    private String country;
    private String province;
    private String city;
    private String address;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
