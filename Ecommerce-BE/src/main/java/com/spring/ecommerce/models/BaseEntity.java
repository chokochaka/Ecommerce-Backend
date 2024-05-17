package com.spring.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
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
public abstract class BaseEntity<T extends Serializable> implements Persistable<T> {

    @Id
    @GeneratedValue
    private T id;

    @Column(updatable = false, nullable = false)
    @CreatedDate
    private Instant createdOn;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant lastUpdatedOn;

    @Transient
    @Override
    public boolean isNew() {
        return id == null;
    }
}