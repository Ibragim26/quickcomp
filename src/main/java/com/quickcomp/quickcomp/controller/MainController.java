package com.quickcomp.quickcomp.controller;

import com.quickcomp.quickcomp.model.entity.User;
import com.quickcomp.quickcomp.service.impl.UserServiceImpl;
import com.quickcomp.quickcomp.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String start(){
        return "index";
    }

    @GetMapping("/order")
    public String authorizedPage(){
        return "order";
    }

    @GetMapping("/registration")
    public String registration(){

        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/login";
    }
}
