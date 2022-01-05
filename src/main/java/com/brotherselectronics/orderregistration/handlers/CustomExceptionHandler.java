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

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<ErrorModel> handleConflict(NotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        var errorModel =  ErrorModel.builder()
                .httpStatus(status)
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorModel, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        var errorModel = ErrorModel.builder()
                .httpStatus(status)
                .timestamp(LocalDateTime.now())
                .build();

        errorModel.setErrorList(ex.getFieldErrors());

        return new ResponseEntity<>(errorModel, status);
    }
}