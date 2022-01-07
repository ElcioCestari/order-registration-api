package com.brotherselectronics.orderregistration.domains.constraints;

import com.brotherselectronics.orderregistration.repositories.BaseRepository;
import com.brotherselectronics.orderregistration.utils.InstancesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ExistsValidator implements ConstraintValidator<ConstraintExists, String> {

    /**
     * TODO existe uma falha nos testes, principalmente nos de controllers pois não esta sendo possível mockar
     * o comportamento do ExistsValidator, mesmo utilizando o mockito when(), ele esta chamando o metodo real
     * e lançando exceção. Portanto deve ser verificado ou uma melhoria no projeto ou remover a funcionalidade do ExistsValidator
     */
    private BaseRepository repository;
    @Autowired private InstancesUtils instancesUtils;

    @Override
    public void initialize(ConstraintExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        repository = instancesUtils.getRepository(constraintAnnotation.entityRepository());
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return  repository.findById(id).isPresent();
    }
}
