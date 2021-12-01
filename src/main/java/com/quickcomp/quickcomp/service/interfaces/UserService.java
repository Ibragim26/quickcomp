package com.quickcomp.quickcomp.service.interfaces;

import com.quickcomp.quickcomp.model.entity.User;
import com.quickcomp.quickcomp.model.repository.UserRepository;

public interface UserService {

    boolean createUser(User user);
    User findById(Long id);

}
