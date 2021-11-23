package com.quickcomp.quickcomp.model.repository;

import com.quickcomp.quickcomp.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
