package com.quickcomp.quickcomp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderForPersistDTO {

    private Long product;
    private String address;
    private Date date;
    private Long orderStatus;

}
