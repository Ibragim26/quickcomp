package com.quickcomp.quickcomp.service.impl;

import com.quickcomp.quickcomp.model.entity.Order;
import com.quickcomp.quickcomp.model.entity.OrderStatus;
import com.quickcomp.quickcomp.model.entity.Product;
import com.quickcomp.quickcomp.model.repository.OrderRepository;
import com.quickcomp.quickcomp.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.getById(id);
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    private Product product;
    private String address;
    private Date date = new Date();
    private OrderStatus orderStatus;

    @Override
    public void update(Long id, Order order) {
        orderRepository.getById(id).setAddress(order.getAddress());
        if (orderRepository.getById(id).getDate() != null)
            orderRepository.getById(id).setDate(order.getDate());
        if (orderRepository.getById(id).getOrderStatus() != null)
            orderRepository.getById(id).setOrderStatus(order.getOrderStatus());
        if (orderRepository.getById(id).getProduct() != null)
            orderRepository.getById(id).setProduct(order.getProduct());
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
}
