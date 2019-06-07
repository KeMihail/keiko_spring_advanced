package com.epam.keikom.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class NoHandlerFoundControllerAdvice {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404(Exception ex) {

        return "404";
    }

    /*@ExceptionHandler(InternalServerErrorException.class)
    public String handle500(Exception ex) {

        return "500";
    }*/
}