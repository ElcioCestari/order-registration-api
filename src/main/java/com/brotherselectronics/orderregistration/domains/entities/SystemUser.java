package com.brotherselectronics.orderregistration.domains.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class SystemUser extends User {

    private final String login;
    private final String password;

    public SystemUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.login = username;
        this.password = password;
    }
}