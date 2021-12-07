package com.quickcomp.quickcomp.dto;

import com.quickcomp.quickcomp.model.entity.Product;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {

    private long id;
    private String product;
    private Date date;
    private String address;
    private String orderStatus;
}
