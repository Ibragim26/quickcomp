package com.quickcomp.quickcomp.service.interfaces;

import com.quickcomp.quickcomp.dto.OrderDTO;
import com.quickcomp.quickcomp.dto.OrderForPersistDTO;
import com.quickcomp.quickcomp.model.entity.Order;

import java.util.List;

public interface OrderService {


    OrderDTO getById(Long id);

    Order save(OrderForPersistDTO order);

    void delete(Long id);

    List<OrderDTO> getAll();
}
