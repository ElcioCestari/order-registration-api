package com.brotherselectronics.orderregistration.handlers;

import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, NotFoundException.class })
    protected ResponseEntity<Object> handleConflict( RuntimeException ex, WebRequest request) {

        var bodyOfResponse = new StringBuilder();

        //TODO must to be improve this functionallity
        bodyOfResponse
                .append("status:" + 404)
                .append(" message: " +ex.getMessage());

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}