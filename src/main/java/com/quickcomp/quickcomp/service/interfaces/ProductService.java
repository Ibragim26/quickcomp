package com.quickcomp.quickcomp.service.interfaces;

import com.quickcomp.quickcomp.dto.ProductCategoryDTO;
import com.quickcomp.quickcomp.dto.ProductForPersistDTO;
import com.quickcomp.quickcomp.model.entity.Product;

import java.util.List;

public interface ProductService {

    ProductCategoryDTO getById(Long id);

    Product save(ProductForPersistDTO product);

    void delete(Long id);

    List<ProductCategoryDTO> getAll();
}
