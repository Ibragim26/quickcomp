package com.quickcomp.quickcomp.model.repository;

import com.quickcomp.quickcomp.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
