package com.quickcomp.quickcomp.service.interfaces;

import com.quickcomp.quickcomp.model.entity.Order;

import java.util.List;

public interface OrderService {


    Order getById(Long id);

    void save(Order order);

    void update(Long id, Order order);
    void delete(Long id);

    List<Order> getAll();
}
