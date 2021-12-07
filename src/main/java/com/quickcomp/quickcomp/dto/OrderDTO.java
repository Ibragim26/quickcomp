package com.quickcomp.quickcomp.dto;

import com.quickcomp.quickcomp.model.entity.Order;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {

    private long id;
    private String product;
    private Date date;
    private String address;
    private String orderStatus;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.product = order.getProduct().getName();
        this.date = order.getDate();
        this.address = order.getAddress();
        this.orderStatus = order.getOrderStatus().getStatus();
    }

    public OrderDTO() {
    }
}
