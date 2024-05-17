package com.spring.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private String roleName;

    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> users;

    @Column(updatable = false, nullable = false)
    @CreatedDate
    private Instant CreatedOn;
}