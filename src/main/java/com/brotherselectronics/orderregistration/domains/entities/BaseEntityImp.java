package com.brotherselectronics.orderregistration.domains.entities;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import static java.util.Objects.isNull;
import static java.util.UUID.randomUUID;

@Data
public abstract class BaseEntityImp implements BaseEntity {
    @NotBlank
    protected String id;

    protected BaseEntityImp(String id) {
        if (isNull(id)) {
            this.id = randomUUID().toString();
        } else {
            this.id = id;
        }
    }

    protected BaseEntityImp() {
        this(randomUUID().toString());
    }

}
