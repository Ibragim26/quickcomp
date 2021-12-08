package com.quickcomp.quickcomp.controller;

import com.quickcomp.quickcomp.dto.OrderDTO;
import com.quickcomp.quickcomp.dto.OrderForPersistDTO;
import com.quickcomp.quickcomp.model.entity.Order;
import com.quickcomp.quickcomp.service.interfaces.OrderService;
import com.quickcomp.quickcomp.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;
    private UserService userService;
    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OrderDTO order = orderService.getById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<OrderDTO>> getAll(){
        List<OrderDTO> orders = orderService.getAll();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Order> saveOrder(@RequestBody OrderForPersistDTO order){
        HttpHeaders headers = new HttpHeaders();

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Order temp = orderService.save(order);
        return new ResponseEntity<>(temp, headers, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderForPersistDTO order){
        HttpHeaders headers = new HttpHeaders();
        if ((order) == null || (id == null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Order temp = orderService.save(order);
        return new ResponseEntity<>(temp, headers, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id){
        OrderDTO order = orderService.getById(id);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
