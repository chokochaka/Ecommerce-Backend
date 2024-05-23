package com.spring.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name"),
        })
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class Category extends BaseEntity<Long> {

    private String name;

    private String description;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    private Set<Product> products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    @JsonBackReference
    private ParentCategory parentCategory;

    @Transient
    private String parentCategoryName;

    @PostLoad
    @PostPersist
    private void setProductId() {
        if (this.parentCategory != null) {
            this.parentCategoryName = this.parentCategory.getParentCategoryName();
        }
    }
}
