package com.brotherselectronics.orderregistration.domains.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    ELETRONIC("Eletrônicos"),
    NOT_DEFINED("Sem categoria");

    private final String value;
}
