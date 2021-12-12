package com.quickcomp.quickcomp.dto;

import com.quickcomp.quickcomp.model.entity.Product;
import lombok.Data;

@Data
public class ProductCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private double price;

    private Long category;


    private String categoryName;

    public ProductCategoryDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.categoryName = product.getCategory().getCategory();
    }

    public ProductCategoryDTO() {
    }
}
