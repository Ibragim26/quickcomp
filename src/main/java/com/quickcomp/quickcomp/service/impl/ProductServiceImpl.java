package com.quickcomp.quickcomp.service.impl;

import com.quickcomp.quickcomp.model.entity.Product;
import com.quickcomp.quickcomp.model.repository.ProductRepository;
import com.quickcomp.quickcomp.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void update(Long id, Product product) {
        productRepository.findById(id).orElse(null)
                .setName(product.getName());
        productRepository.findById(id).orElse(null)
                .setPrice(product.getPrice());
        productRepository.findById(id).orElse(null)
                .setDescription(product.getDescription());
        if (productRepository.findById(id).orElse(null).getCategory() != null)
            productRepository.findById(id).orElse(null)
                    .setCategory(product.getCategory());
        if (productRepository.findById(id).orElse(null).getOrders() != null)
            productRepository.findById(id).orElse(null)
                    .setOrders(product.getOrders());
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
