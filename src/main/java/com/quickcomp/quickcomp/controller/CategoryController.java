package com.quickcomp.quickcomp.controller;

import com.quickcomp.quickcomp.model.entity.Category;
import com.quickcomp.quickcomp.service.impl.CategoryServiceImpl;
import com.quickcomp.quickcomp.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
//@CrossOrigin
public class CategoryController {

    private CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Category category = categoryService.getById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Category>> getAll(){
        List<Category> categories = categoryService.getAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category){
        HttpHeaders headers = new HttpHeaders();
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        categoryService.save(category);
        return new ResponseEntity<>(category, headers, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category){
        HttpHeaders headers = new HttpHeaders();
        if ((category) == null || (id == null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        categoryService.save(category);
        return new ResponseEntity<>(category, headers, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id){
        Category category = categoryService.getById(id);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
