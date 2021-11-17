package com.quickcomp.quickcomp.controller;

import com.quickcomp.quickcomp.model.entity.Order;
import com.quickcomp.quickcomp.model.entity.Product;
import com.quickcomp.quickcomp.service.impl.OrderServiceImpl;
import com.quickcomp.quickcomp.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController
{
    private ProductServiceImpl productService;
    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getCategoryById(@PathVariable Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Product>> getAll(){
        List<Product> products = productService.getAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Product> saveCategory(@RequestBody Product product){
        HttpHeaders headers = new HttpHeaders();
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        productService.save(product);
        return new ResponseEntity<>(product, headers, HttpStatus.CREATED);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateCategory(@PathVariable Long id, @RequestBody Product product){
        HttpHeaders headers = new HttpHeaders();
        if ((product) == null || (id == null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        productService.update(id, product);
        return new ResponseEntity<>(product, headers, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteCategory(@PathVariable Long id){
        Product product = productService.getById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
