package com.quickcomp.quickcomp.controller;

import com.quickcomp.quickcomp.model.entity.Order;
import com.quickcomp.quickcomp.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
//@CrossOrigin
public class OrderController {

    private OrderServiceImpl orderService;
    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Order order = orderService.getById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Order>> getAll(){
        List<Order> orders = orderService.getAll();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order){
        HttpHeaders headers = new HttpHeaders();

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        orderService.save(order);
        return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order){
        HttpHeaders headers = new HttpHeaders();
        if ((order) == null || (id == null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        orderService.save(order);
        return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id){
        Order order = orderService.getById(id);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
