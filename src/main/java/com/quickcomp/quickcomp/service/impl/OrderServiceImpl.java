package com.quickcomp.quickcomp.service.impl;

import com.quickcomp.quickcomp.dto.OrderDTO;
import com.quickcomp.quickcomp.model.entity.Order;
import com.quickcomp.quickcomp.model.repository.OrderRepository;
import com.quickcomp.quickcomp.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDTO getById(Long id) {
        OrderDTO order = new OrderDTO();
        order.setId(id);
        order.setAddress(orderRepository.getById(id).getAddress());
        order.setDate(orderRepository.getById(id).getDate());
        order.setOrderStatus(orderRepository.getById(id).getOrderStatus().getStatus());
        order.setProduct(orderRepository.getById(id).getProduct().getName());
        return order;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDTO> getAll() {
        List<OrderDTO> list = orderRepository.findAll()
                .stream().map(element -> {
                    OrderDTO order = new OrderDTO();
                    order.setId(element.getId());
                    order.setOrderStatus(element.getOrderStatus().getStatus());
                    order.setDate(element.getDate());
                    order.setAddress(element.getAddress());
                    order.setProduct(element.getProduct().getName());
                    return order;
                }).collect(Collectors.toList());
        return list;
    }
}
