package com.brotherselectronics.orderregistration.domains.constraints;

import com.brotherselectronics.orderregistration.repositories.BaseRepository;
import com.brotherselectronics.orderregistration.repositories.OrderRepository;
import com.brotherselectronics.orderregistration.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistsValidator implements ConstraintValidator<ConstraintExists, String> {

    private BaseRepository repository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;

    @Override
    public void initialize(ConstraintExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        switch (constraintAnnotation.repository()) {
            case ORDER: repository = orderRepository; break;
            case PRODUCT: repository = productRepository; break;
            default: throw new IllegalArgumentException("RepositoryDomain ["
                    +constraintAnnotation.repository()+"] not implemented yet");
        }
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return repository.findById(id).isPresent();
    }
}
