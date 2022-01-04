package com.brotherselectronics.orderregistration.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
public class ErrorModel {

    private final HttpStatus httpStatus;
    private final LocalDateTime timestamp;
    private final String message;
    private final String details;
    private final String field;
    private final String defaultMessage;
    private final Object rejectedValue;

}