package com.quickcomp.quickcomp.service.impl;

import com.quickcomp.quickcomp.dto.ProductCategoryDTO;
import com.quickcomp.quickcomp.model.entity.Product;
import com.quickcomp.quickcomp.model.repository.CategoryRepository;
import com.quickcomp.quickcomp.model.repository.ProductRepository;
import com.quickcomp.quickcomp.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductCategoryDTO getById(Long id) {
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setId(id);
        productCategoryDTO.setCategory(productRepository.getById(id).getCategory().getCategory());
        productCategoryDTO.setDescription(productRepository.getById(id).getDescription());
        productCategoryDTO.setName(productRepository.getById(id).getName());
        productCategoryDTO.setPrice(productRepository.getById(id).getPrice());
        return productCategoryDTO;
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductCategoryDTO> getAll() {

        List<ProductCategoryDTO> list = productRepository.findAll()
                .stream().map(product -> {
                    ProductCategoryDTO temp = new ProductCategoryDTO();
                    temp.setId(product.getId());
                    temp.setCategory(product.getCategory().getCategory());
                    temp.setDescription(product.getDescription());
                    temp.setName(product.getName());
                    temp.setPrice(product.getPrice());
                    return temp;
        }).collect(Collectors.toList());
        return list;
    }
}
