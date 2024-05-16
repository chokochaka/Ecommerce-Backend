package com.spring.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Instant;
import java.util.Set;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder

public class Role {
    @Id
    @Column(name = "role_name")
    String roleName;

    String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Set<User> users;

    @Column(updatable = false, nullable = false)
    @CreatedDate
    private Instant CreatedOn;
}