package com.brotherselectronics.orderregistration.domains.entities;

import lombok.Data;

@Data
public abstract class BaseEntityImp implements BaseEntity {
    private String id;
}
