package com.quickcomp.quickcomp.dto;

import lombok.Data;

@Data
public class ProductForPersistDTO {

    private String name;
    private String description;
    private Double price;
    private Long category;

}
