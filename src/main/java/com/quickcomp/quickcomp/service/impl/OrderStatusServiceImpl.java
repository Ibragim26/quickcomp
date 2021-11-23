package com.quickcomp.quickcomp.service.impl;


import com.quickcomp.quickcomp.model.entity.OrderStatus;
import com.quickcomp.quickcomp.model.repository.OrderStatusRepository;
import com.quickcomp.quickcomp.service.interfaces.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusServiceImpl implements StatusService {

    private OrderStatusRepository orderStatusRepository;

    @Autowired
    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public void save(OrderStatus orderStatus) {
        orderStatusRepository.save(orderStatus);
    }

    @Override
    public List<OrderStatus> getAll() {
        return orderStatusRepository.findAll();
    }
}
