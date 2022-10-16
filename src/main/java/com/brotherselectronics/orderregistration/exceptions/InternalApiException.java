package com.brotherselectronics.orderregistration.exceptions;

public class InternalApiException extends RuntimeException {
    public InternalApiException() {
        super("Internal error in api");
    }

}
