package com.brotherselectronics.fakers;

import com.brotherselectronics.orderregistration.domains.dtos.UserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.UserResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.BaseEntityImp;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

public class UserFaker extends BaseEntityImp implements EntityFake<SystemUser, UserRequestDTO, UserResponseDTO> {

    private final String login = RandomString.make(10);
    private final String password = "$2a$10$CKtbFy2R0qxNLPF.d6Jaeuf1UQOmDxSQFh/1ToxNEaxgxgnIy6SUC"; //elcio123
    private final String name = RandomString.make(10);

    @Override
    public SystemUser getEntity() {
        return SystemUser.Factory.buildAdmin(login, password);
    }

    @Override
    public UserRequestDTO getRequestDTO() {
        return UserRequestDTO.builder()
                .login(login)
                .name(name)
                .password(password)
                .build();
    }

    @Override
    public UserResponseDTO getResponseDTO() {
        return UserResponseDTO.builder()
                .login(login)
                .name(name)
                .build();
    }
}