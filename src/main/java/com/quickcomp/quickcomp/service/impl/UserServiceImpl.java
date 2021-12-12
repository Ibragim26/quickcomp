package com.quickcomp.quickcomp.service.impl;

import com.quickcomp.quickcomp.model.entity.User;
import com.quickcomp.quickcomp.model.entity.enums.Role;
import com.quickcomp.quickcomp.model.repository.UserRepository;
import com.quickcomp.quickcomp.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.quickcomp.quickcomp.config.SecurityConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Transactional
    @Override
    public boolean createUser(User user) {
        if (userRepository.findAll().size() == 0){
            user.getRoles().add(Role.ROLE_ADMIN);
            user.getRoles().add(Role.ROLE_SUPER_ADMIN);
        }
        user.getRoles().add(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }


    @Override
    public void changeUserRole(Long id) {

        User temp = userRepository.getById(id);
        userRepository.deleteById(id);
        temp.getRoles().add(Role.ROLE_ADMIN);
        userRepository.save(temp);
    }


    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
