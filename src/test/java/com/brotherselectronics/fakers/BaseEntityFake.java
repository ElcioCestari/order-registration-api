package com.brotherselectronics.fakers;

import lombok.Data;

import static java.util.UUID.randomUUID;

@Data
public abstract class BaseEntityFake {
    public static final String FAKE_ID = randomUUID().toString();
}