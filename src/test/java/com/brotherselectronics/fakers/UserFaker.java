package com.brotherselectronics.fakers;

import com.brotherselectronics.orderregistration.domains.dtos.UserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.UserResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.BaseEntity;
import com.brotherselectronics.orderregistration.domains.entities.User;

public class UserFaker extends BaseEntity implements EntityFake<User, UserRequestDTO, UserResponseDTO>{

    private final String login = "elcio";
    private final String password = "elcio123";
    private final String name = "elcio";

    @Override
    public User getEntity() {
        return new User(login, password, name);
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