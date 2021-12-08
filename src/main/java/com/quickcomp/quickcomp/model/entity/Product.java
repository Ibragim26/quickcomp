package com.quickcomp.quickcomp.model.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
//@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(length = 1500)
    private String description;
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(String name, String description, Double price, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}
