package com.brotherselectronics.orderregistration.domains.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@AllArgsConstructor
@Getter
public enum GrantedAuthority {

    ADMIN(new SimpleGrantedAuthority(Role.ADMIN.getAuthority())),
    CUSTOMER(new SimpleGrantedAuthority(Role.CUSTOMER.getAuthority()));

    private final SimpleGrantedAuthority authority;

}