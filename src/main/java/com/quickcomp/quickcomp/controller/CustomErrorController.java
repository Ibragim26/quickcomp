package com.quickcomp.quickcomp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    @GetMapping("/error")
    public String forbiddenException(){
        return "error/error403";
    }

//    public String handleError(HttpServletRequest request) {
//
//
//        String errorPage = "error/error";
//
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//        if (status != null) {
//            int statusCode = Integer.valueOf(status.toString());
//
//            errorPage = errorPage + statusCode;
//
//            if (statusCode == HttpStatus.NOT_FOUND.value()) {
//                errorPage = "error/error404";
//
//            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
//                errorPage = "error/error403";
//
//            } else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
//                errorPage = "error/error405";
//
//            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                errorPage = "error/error500";
//
//            }else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
//                errorPage = "error/error400";
//            }
//        }
//        return errorPage;
//    }
}