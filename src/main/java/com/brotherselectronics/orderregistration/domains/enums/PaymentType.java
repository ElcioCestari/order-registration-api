package com.brotherselectronics.orderregistration.domains.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {
    INVOICE("Boleto"),
    CHECK("Cheque"),
    CREDIT_CARD("Cartão de Crédito"),
    DEBIT_CARD("Cartão de Débito"),
    MONEY("Dinheiro em Espécie"),
    PIX("PIX");

    private final String description;
}
