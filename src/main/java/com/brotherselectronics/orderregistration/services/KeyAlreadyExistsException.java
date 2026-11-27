package com.brotherselectronics.orderregistration.services;

import org.springframework.dao.DuplicateKeyException;

public class KeyAlreadyExistsException extends RuntimeException {
    public KeyAlreadyExistsException(DuplicateKeyException e) {
        super("A record with the same unique key already exists", e);
    }
}
