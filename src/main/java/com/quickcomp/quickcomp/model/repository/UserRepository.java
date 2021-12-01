package com.quickcomp.quickcomp.model.repository;

import com.quickcomp.quickcomp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
