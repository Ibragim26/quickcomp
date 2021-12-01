package com.quickcomp.quickcomp.controller;

import com.quickcomp.quickcomp.model.entity.OrderStatus;
import com.quickcomp.quickcomp.service.impl.OrderStatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class OrderStatusController {

    private OrderStatusServiceImpl orderStatusService;

    @Autowired
    public OrderStatusController(OrderStatusServiceImpl orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<OrderStatus>> getAll(){
        List<OrderStatus> orderStatuses = orderStatusService.getAll();
        if (orderStatuses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderStatuses, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<OrderStatus> saveProduct(@RequestBody OrderStatus orderStatus){
        HttpHeaders headers = new HttpHeaders();
        if (orderStatus == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        orderStatusService.save(orderStatus);
        return new ResponseEntity<>(orderStatus, headers, HttpStatus.CREATED);

    }

}
