package com.brotherselectronics.orderregistration.domains.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SystemUserCreateRequestDTO extends SystemUserRequestDTO {
    @NotBlank
    private String username;

    @Override
    @NotBlank
    public String getPassword() {
        return super.getPassword();
    }

}