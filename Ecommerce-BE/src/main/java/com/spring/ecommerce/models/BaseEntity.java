package com.spring.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.bind.DefaultValue;
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
public abstract class BaseEntity<T extends Serializable> implements Persistable<T> {

    @Id
    @GeneratedValue
    private T id;

    @Column(updatable = false, nullable = false)
    @CreatedDate
    private Instant CreatedOn;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant LastUpdatedOn;

    @Transient
    @Override
    public boolean isNew() {
        return id == null;
    }
}