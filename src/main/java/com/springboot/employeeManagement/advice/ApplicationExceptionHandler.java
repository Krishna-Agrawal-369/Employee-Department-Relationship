package com.springboot.employeeManagement.advice;


import com.springboot.employeeManagement.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errorList = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(e -> errorList.add(e.getField() + " : " + e.getDefaultMessage()));
        return new ResponseEntity<>(new Response(false, -1, errorList), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleAllExceptions(Exception exception) {
        return new ResponseEntity<>(new Response(false, -1, Collections.singletonList(exception.getLocalizedMessage())), HttpStatus.BAD_REQUEST);
    }
}
