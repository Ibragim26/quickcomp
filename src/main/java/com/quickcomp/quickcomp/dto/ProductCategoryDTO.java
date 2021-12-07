package com.quickcomp.quickcomp.dto;

import com.quickcomp.quickcomp.model.entity.Product;
import lombok.Data;

@Data
public class ProductCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String category;

    public ProductCategoryDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory().getCategory();
    }

    public ProductCategoryDTO() {
    }
}
