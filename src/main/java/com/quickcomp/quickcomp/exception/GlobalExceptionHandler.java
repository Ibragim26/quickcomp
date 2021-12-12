package com.quickcomp.quickcomp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(ForbiddenException.class)
//    public String handleForbiddenException(ForbiddenException exception, WebRequest request){
//        ErrorDetails errorDetails = new ErrorDetails(
//                new Date(), exception.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
//    }

}
