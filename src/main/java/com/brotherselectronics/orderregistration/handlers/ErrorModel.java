package com.brotherselectronics.orderregistration.handlers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@ToString
@Builder
public class ErrorModel {

    private HttpStatus httpStatus;
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private List<CustomFieldError> errorList;

    public void setErrorList(List<FieldError> fieldErrors) {
        this.errorList = convertToCustomFieldError(fieldErrors);
    }

    private List<CustomFieldError> convertToCustomFieldError(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(e -> new CustomFieldError(e.getField(), e.getRejectedValue()))
                .collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    private class CustomFieldError {
        private String field;
        private Object rejectValue;
    }
}