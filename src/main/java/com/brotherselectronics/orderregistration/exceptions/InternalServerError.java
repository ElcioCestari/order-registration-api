package com.brotherselectronics.orderregistration.exceptions;

public class InternalServerError extends RuntimeException {

    public InternalServerError() {
        super("Ops! Algo deu errado, tente novamente mais tarde.");
    }
}
