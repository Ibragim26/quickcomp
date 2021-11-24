package com.quickcomp.quickcomp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StarterController {

    @RequestMapping(value = "start")
    public String start(){
        return "index";
    }
}
