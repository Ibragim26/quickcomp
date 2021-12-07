package com.quickcomp.quickcomp.service.interfaces;

import com.quickcomp.quickcomp.dto.ProductCategoryDTO;
import com.quickcomp.quickcomp.model.entity.Product;

import java.util.List;

public interface ProductService {

    ProductCategoryDTO getById(Long id);

    void save(Product product);

    void delete(Long id);

    List<ProductCategoryDTO> getAll();
}
