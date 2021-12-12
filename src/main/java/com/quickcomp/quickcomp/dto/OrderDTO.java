package com.quickcomp.quickcomp.dto;

import com.quickcomp.quickcomp.model.entity.Order;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {

    private long id;
    private String productName;
    private Date date;
    private String address;
    private String orderStatusName;
    private Long product;
    private Long orderStatus;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.productName = order.getProduct().getName();
        this.date = order.getDate();
        this.address = order.getAddress();
        this.orderStatusName = order.getOrderStatus().getStatus();
    }

    public OrderDTO() {
    }
}
