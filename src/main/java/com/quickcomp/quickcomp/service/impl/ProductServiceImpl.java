package com.quickcomp.quickcomp.service.impl;

import com.quickcomp.quickcomp.dto.ProductCategoryDTO;
import com.quickcomp.quickcomp.model.entity.Category;
import com.quickcomp.quickcomp.model.entity.Product;
import com.quickcomp.quickcomp.model.repository.CategoryRepository;
import com.quickcomp.quickcomp.model.repository.ProductRepository;
import com.quickcomp.quickcomp.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public ProductCategoryDTO getById(Long id) {
        Product temp = productRepository.getById(id);
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO(temp);
        productCategoryDTO.setId(temp.getId());
        productCategoryDTO.setCategoryName(temp.getCategory().getCategory());
        return productCategoryDTO;
    }

    @Override
    public Product save(ProductCategoryDTO product) {
        Category category = categoryRepository.findById(product.getCategory()).orElse(null);
        Product temp = new Product(product.getName(), product.getDescription(), product.getPrice(), category);
        productRepository.save(temp);
        return temp;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductCategoryDTO> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductCategoryDTO> list = products
                .stream().map(product -> {
                    ProductCategoryDTO temp = new ProductCategoryDTO(product);
                    return temp;
        }).collect(Collectors.toList());
        return list;
    }
}
