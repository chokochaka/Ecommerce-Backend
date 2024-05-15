package com.spring.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass
@NoArgsConstructor
@Getter
@ToString
public abstract class BaseEntity<Long extends Serializable> implements Persistable<Long> {

    @Id
    private Long id;

    @Column(updatable = false, nullable = false)
    @CreatedDate
    private Instant dateCreated;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant dateModified;

    @Transient
    @Override
    public boolean isNew() {
        return id == null;
    }
}