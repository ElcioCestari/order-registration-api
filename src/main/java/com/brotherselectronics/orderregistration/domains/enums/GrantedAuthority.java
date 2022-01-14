package com.brotherselectronics.orderregistration.domains.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@AllArgsConstructor
@Getter
public enum GrantedAuthority {

    ADMIN( new SimpleGrantedAuthority("ROLE_ADMIN")),
    USER(new SimpleGrantedAuthority("ROLE_USER"));

    private final SimpleGrantedAuthority authority;

}