package com.quickcomp.quickcomp.service.interfaces;

import com.quickcomp.quickcomp.model.entity.Order;

import java.util.List;

public interface OrderStatusService {

    void save(Order order);

    List<Order> getAll();
}
