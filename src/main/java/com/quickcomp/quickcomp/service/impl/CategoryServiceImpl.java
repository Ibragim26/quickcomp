package com.quickcomp.quickcomp.service.impl;

import com.quickcomp.quickcomp.model.entity.Category;
import com.quickcomp.quickcomp.model.repository.CategoryRepository;
import com.quickcomp.quickcomp.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {


    private CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



    @Override
    public Category getById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return category;
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void update(Long id, Category category) {
        categoryRepository.getById(id).setCategoryName(category.getCategoryName());
        categoryRepository.getById(id).setRating(category.getRating());

        if (categoryRepository.getById(id).getProducts() != null)
            categoryRepository.getById(id).setProducts(category.getProducts());

    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
