package com.quickcomp.quickcomp.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_status")
@Getter
@Setter
//@Builder
@NoArgsConstructor
@EqualsAndHashCode
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

}