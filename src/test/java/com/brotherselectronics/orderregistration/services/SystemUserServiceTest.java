package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserUpdateRequestDTO;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.domains.enums.GrantedAuthority;
import com.brotherselectronics.orderregistration.domains.mappers.SystemUserMapper;
import com.brotherselectronics.orderregistration.repositories.UserRepository;
import com.brotherselectronics.orderregistration.testsutils.GenericFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.brotherselectronics.orderregistration.testsutils.GenericFactory.buildObjectOfAnyType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class SystemUserServiceTest {

    private SystemUserService service;

    @Mock
    private UserRepository repository;

    @Autowired
    private SystemUserMapper mapper;

    @Mock
    private MyUserDetailService userDetailService;

    @Autowired
    private  PasswordEncoder encoder;

    @BeforeEach
    void setup() {
        service = new SystemUserService(repository, mapper, userDetailService, encoder);
    }
    @Test
    void wasDefined() {
        assertThat(service).isNotNull();
    }

    @Test
    void merge() {
    }

    @Test
    void ignoreAttributes() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    @SneakyThrows
    void save() {
        var user = SystemUser.Factory.simpleUser("elcio", "elcio");
        when(repository.findById(anyString())).thenReturn(Optional.of(user));

        var dto = buildObjectOfAnyType(SystemUserUpdateRequestDTO.class);
        SystemUserResponseDTO update = service.update((SystemUserRequestDTO) dto, user.getId());
        assertThat(update).isNotNull();
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getLoggedUser() {
    }
}