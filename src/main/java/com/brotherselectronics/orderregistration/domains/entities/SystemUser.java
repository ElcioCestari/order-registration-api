package com.brotherselectronics.orderregistration.domains.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Document("Users")
public class SystemUser implements BaseEntity, UserDetails {

    @Id
    private final UUID uuid;

    @Indexed(unique = true)
    private final String username;

    private String password;

    private Collection<SimpleGrantedAuthority> authorities;

    public SystemUser(String username, String password, Collection<SimpleGrantedAuthority> authorities) {
        this.uuid = randomUUID();
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}