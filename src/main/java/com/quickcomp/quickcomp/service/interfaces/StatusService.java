package com.quickcomp.quickcomp.service.interfaces;

import java.util.List;

import com.quickcomp.quickcomp.model.entity.OrderStatus;
import com.quickcomp.quickcomp.model.entity.Product;



public interface StatusService {

    void save(OrderStatus orderStatus);

    List<OrderStatus> getAll();


}
