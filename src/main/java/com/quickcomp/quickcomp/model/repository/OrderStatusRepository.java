package com.quickcomp.quickcomp.model.repository;

import com.quickcomp.quickcomp.model.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
}
