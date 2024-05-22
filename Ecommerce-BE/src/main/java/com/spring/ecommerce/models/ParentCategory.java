package com.spring.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "parent_categories",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "parent_category_name"),
        })
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class ParentCategory extends BaseEntity<Long> {
    @Column(name = "parent_category_name")
    private String parentCategoryName;

    private String description;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Category> categories;


}
