package com.brotherselectronics.orderregistration.domains.constraints;

import com.brotherselectronics.orderregistration.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistsValidator implements ConstraintValidator<ConstraintExists, String> {

    @Autowired
    private OrderRepository repository;

    @Override
    public void initialize(ConstraintExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return repository.findById(id).isPresent();
    }
}
