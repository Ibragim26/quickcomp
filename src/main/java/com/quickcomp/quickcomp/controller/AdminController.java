package com.quickcomp.quickcomp.controller;

import com.quickcomp.quickcomp.model.entity.User;
import com.quickcomp.quickcomp.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
public class AdminController {

    private final UserService service;

    @Autowired
    public AdminController(UserService service) {
        this.service = service;
    }

    @GetMapping("users")
    public String getUsers(Model model){
        model.addAttribute("users", service.getUsers());
        return "user-list";
    }

    @PostMapping("change/{id}")
    public String userEdit(@PathVariable Long id){
        service.changeUserRole(id);
        return "redirect:/users";
    }
}
