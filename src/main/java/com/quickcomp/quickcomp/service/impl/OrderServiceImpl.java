package com.quickcomp.quickcomp.service.impl;

import com.quickcomp.quickcomp.dto.OrderDTO;
import com.quickcomp.quickcomp.dto.OrderForPersistDTO;
import com.quickcomp.quickcomp.model.entity.Order;
import com.quickcomp.quickcomp.model.entity.OrderStatus;
import com.quickcomp.quickcomp.model.entity.Product;
import com.quickcomp.quickcomp.model.repository.OrderRepository;
import com.quickcomp.quickcomp.model.repository.OrderStatusRepository;
import com.quickcomp.quickcomp.model.repository.ProductRepository;
import com.quickcomp.quickcomp.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, OrderStatusRepository orderStatusRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public OrderDTO getById(Long id) {
        OrderDTO order = new OrderDTO(orderRepository.findById(id).orElse(null));
        return order;
    }
    @Override
    public Order save(OrderForPersistDTO order) {
        Product product = productRepository.findById(order.getProduct()).orElse(null);
        com.quickcomp.quickcomp.model.entity.OrderStatus status = orderStatusRepository.findById(order.getOrderStatus()).orElse(null);

        Order temp = new Order(product, order.getAddress(), order.getDate(), status);
        orderRepository.save(temp);
        return temp;
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDTO> getAll() {
        List<OrderDTO> list = orderRepository.findAll()
                .stream().map(element -> {
                    OrderDTO order = new OrderDTO(element);
                    return order;
                }).collect(Collectors.toList());
        return list;
    }
}
