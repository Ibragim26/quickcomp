package com.quickcomp.quickcomp.controller;

import com.quickcomp.quickcomp.dto.ProductCategoryDTO;
import com.quickcomp.quickcomp.model.entity.Product;
import com.quickcomp.quickcomp.service.impl.ProductServiceImpl;
import com.quickcomp.quickcomp.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
//@CrossOrigin
public class ProductController
{
    private ProductService productService;
    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductCategoryDTO> getProductById(@PathVariable Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ProductCategoryDTO product = productService.getById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<ProductCategoryDTO>> getAll(){
        List<ProductCategoryDTO> products = productService.getAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/post")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_SUPER_ADMIN')")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductCategoryDTO product){
        HttpHeaders headers = new HttpHeaders();
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product temp = productService.save(product);
        return new ResponseEntity<>(temp, headers, HttpStatus.CREATED);

    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_SUPER_ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductCategoryDTO product){
        HttpHeaders headers = new HttpHeaders();
        if ((product) == null || (id == null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product temp = productService.save(product);
        return new ResponseEntity<>(temp, headers, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_SUPER_ADMIN')")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
        ProductCategoryDTO product = productService.getById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
