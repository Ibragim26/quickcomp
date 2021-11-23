package com.quickcomp.quickcomp.service.interfaces;

import com.quickcomp.quickcomp.model.entity.Category;

import java.util.List;

public interface CategoryService {

    Category getById(Long id);
    void save(Category category);

    void delete(Long id);
    List<Category> getAll();
}
