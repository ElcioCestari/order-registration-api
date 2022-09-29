package com.brotherselectronics.orderregistration.domains.dtos;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class SystemUserRequestDTO {
    @NotBlank
    private String password;
    @NotBlank
    private String username;
    private Set<SimpleGrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}