package com.brotherselectronics.orderregistration.domains.dtos;

import com.brotherselectronics.orderregistration.domains.enums.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class SystemUserRequestDTO {
    @NotBlank
    private String password;
    private Set<Role> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}