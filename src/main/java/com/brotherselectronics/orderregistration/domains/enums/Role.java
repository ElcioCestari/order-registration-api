package com.brotherselectronics.orderregistration.domains.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"),
    STAFF("STAFF"),
    USER("USER"),
    GUEST("GUEST");

    private final String authority;

}
