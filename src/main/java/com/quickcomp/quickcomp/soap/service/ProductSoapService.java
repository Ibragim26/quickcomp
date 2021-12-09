package com.quickcomp.quickcomp.soap.service;

import com.quickcomp.quickcomp.dto.ProductCategoryDTO;
import com.quickcomp.quickcomp.service.interfaces.ProductService;
import com.quickcomp.quickcomp.soap.product.ProductSoap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSoapService {

    private ProductService service;

    @Autowired
    public ProductSoapService(ProductService service) {
        this.service = service;
    }

    public ProductSoap getProduct(long id) {
        ProductCategoryDTO product = service.getById(id);
        ProductSoap soap = new ProductSoap(product.getId(), product.getName(), product.getDescription(), product.getPrice());
        return soap;
    }
    
}
