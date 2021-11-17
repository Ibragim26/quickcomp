package com.quickcomp.quickcomp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
//@Builder
@NoArgsConstructor
@EqualsAndHashCode
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;
    private String rating;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "category")
    private List<Product> products;
}
