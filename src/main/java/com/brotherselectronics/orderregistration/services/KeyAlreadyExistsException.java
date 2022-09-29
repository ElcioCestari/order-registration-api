package com.brotherselectronics.orderregistration.services;

import org.springframework.dao.DuplicateKeyException;

public class KeyAlreadyExistsException extends RuntimeException {
    public KeyAlreadyExistsException(DuplicateKeyException e) {
    }
}
