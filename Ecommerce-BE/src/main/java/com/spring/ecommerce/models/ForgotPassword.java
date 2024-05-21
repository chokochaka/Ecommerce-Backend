package com.spring.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "forgot_password")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class ForgotPassword {
    @Id
    @GeneratedValue
    private Long id;

    private Integer otp;

    @Column(updatable = false, nullable = false)
    private Instant issuedAt;

    @Column(updatable = false, nullable = false)
    private Instant expiresAt;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;
}
