package com.quickcomp.quickcomp.model.repository;

import com.quickcomp.quickcomp.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
