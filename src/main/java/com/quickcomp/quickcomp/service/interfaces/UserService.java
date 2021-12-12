package com.quickcomp.quickcomp.service.interfaces;

import com.quickcomp.quickcomp.model.entity.User;
import com.quickcomp.quickcomp.model.repository.UserRepository;

import java.util.List;
import java.util.Map;

public interface UserService {

    boolean createUser(User user);
    User findById(Long id);
    User findByUsername(String username);

    List<User> getUsers();
    void changeUserRole(Long id);

}
