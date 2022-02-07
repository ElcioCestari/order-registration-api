package com.brotherselectronics.orderregistration.domains.enums;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("ROLE_ADMIN"),
    STAFF("ROLE_STAFF"),
    USER("ROLE_USER"),
    GUEST("ROLE_GUEST");

    private final String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
