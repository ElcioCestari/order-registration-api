package com.brotherselectronics.orderregistration.handlers;

import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolationException;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  protected ResponseEntity<ErrorModel> genericException(Exception ex, WebRequest request) {
    var errorModel = ErrorModel.builder()
        .httpStatus(INTERNAL_SERVER_ERROR)
        .timestamp(now())
        .message("Ocorreu um erro inesperado, tente novamente mais tarde.")
        .build();
    return new ResponseEntity<>(errorModel, INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = NotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  protected ResponseEntity<ErrorModel> handleConflict(NotFoundException ex, WebRequest request) {
    var errorModel = ErrorModel.builder()
        .httpStatus(NOT_FOUND)
        .timestamp(now())
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(errorModel, NOT_FOUND);
  }

  @ExceptionHandler(value = ConstraintViolationException.class)
  @ResponseStatus(UNPROCESSABLE_ENTITY)
  protected ResponseEntity<ErrorModel> handleBeanValidation(ConstraintViolationException ex, WebRequest request) {
    var errorModel = ErrorModel.builder()
        .httpStatus(UNPROCESSABLE_ENTITY)
        .timestamp(now())
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(errorModel, UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = ConstraintDeclarationException.class)
  @ResponseStatus(UNPROCESSABLE_ENTITY)
  protected ResponseEntity<ErrorModel> handleConstraintDeclarationException(ConstraintDeclarationException ex,
                                                                            WebRequest request) {
    var errorModel = ErrorModel.builder()
        .httpStatus(UNPROCESSABLE_ENTITY)
        .timestamp(now())
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(errorModel, UNPROCESSABLE_ENTITY);
  }

  @Override //TODO -check exception return
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {
    var errorModel = ErrorModel
        .builder()
        .httpStatus(status)
        .timestamp(now())
        .build();
    errorModel.setErrorList(ex.getFieldErrors());
    return new ResponseEntity<>(errorModel, status);
  }
}