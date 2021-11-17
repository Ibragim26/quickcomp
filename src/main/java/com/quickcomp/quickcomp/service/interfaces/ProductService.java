package com.quickcomp.quickcomp.service.interfaces;

import com.quickcomp.quickcomp.model.entity.Product;

import java.util.List;

public interface ProductService {

    Product getById(Long id);

    void save(Product product);

    void update(Long id, Product product);

    void delete(Long id);



    List<Product> getAll();
}
