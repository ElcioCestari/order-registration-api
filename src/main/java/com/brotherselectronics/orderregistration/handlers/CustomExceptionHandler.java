package com.brotherselectronics.orderregistration.handlers;

import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class, NotFoundException.class})
    protected ResponseEntity<ErrorModel> handleConflict(RuntimeException ex, WebRequest request) {
        ErrorModel errorModel = new ErrorModel(HttpStatus.NOT_FOUND, LocalDateTime.now(), ex.getMessage(), null, null,null,null);
        return new ResponseEntity<ErrorModel>(errorModel, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorModel errorModel = new ErrorModel(HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                ex.getParameter().toString(),
                ex.getMessage(),
                ex.getFieldError().getField(),
                ex.getFieldError().getDefaultMessage(),
                ex.getFieldError().getRejectedValue());

        return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);
    }
}