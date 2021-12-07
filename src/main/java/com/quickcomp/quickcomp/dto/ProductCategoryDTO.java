package com.quickcomp.quickcomp.dto;

import lombok.Data;

@Data
public class ProductCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String category;
}
