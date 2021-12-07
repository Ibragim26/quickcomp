package com.quickcomp.quickcomp.model.entity;

import jdk.jfr.Timespan;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking")
@Getter
@Setter
//@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id")
    private Product product;


    private String address;
    @Temporal(TemporalType.DATE)
    private Date date = new Date();


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    public Order(Product product, String address, Date date, OrderStatus orderStatus) {
        this.product = product;
        this.address = address;
        this.date = date;
        this.orderStatus = orderStatus;
    }
}
