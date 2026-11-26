package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserCreateRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserUpdateRequestDTO;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.domains.enums.Role;
import com.brotherselectronics.orderregistration.domains.mappers.SystemUserMapper;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static com.brotherselectronics.orderregistration.testsutils.GenericFactory.buildObjectOfAnyType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
    private PasswordEncoder encoder;

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
    @SneakyThrows
    void update_whenPasswordIsBlank_thenKeepsCurrentPassword() {
        var user = SystemUser.Factory.simpleUser("elcio", "current-hash");
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        var dto = new SystemUserUpdateRequestDTO();
        dto.setAuthorities(Set.of(Role.ADMIN));
        service.update(dto, user.getId());

        assertThat(user.getPassword()).isEqualTo("current-hash");
        assertThat(user.getAuthorities()).extracting("authority").containsExactly(Role.ADMIN.getAuthority());
    }

    @Test
    @SneakyThrows
    void update_whenPasswordIsInformed_thenEncodesItAndKeepsAuthoritiesWhenAbsent() {
        var user = SystemUser.Factory.simpleUser("elcio", "current-hash");
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        var dto = new SystemUserUpdateRequestDTO();
        dto.setPassword("new-password");
        service.update(dto, user.getId());

        assertThat(encoder.matches("new-password", user.getPassword())).isTrue();
        assertThat(user.getAuthorities()).extracting("authority").containsExactly(Role.USER.getAuthority());
    }

    @Test
    void update_whenUserDoesNotExist_thenThrowNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        var dto = new SystemUserUpdateRequestDTO();
        assertThatThrownBy(() -> service.update(dto, "missing")).isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_whenUserExists_thenDeletesIt() {
        var user = SystemUser.Factory.simpleUser("elcio", "elcio");
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        service.delete(user.getId());
        verify(repository).delete(user);
    }

    @Test
    void delete_whenUserDoesNotExist_thenThrowNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.delete("missing")).isInstanceOf(NotFoundException.class);
        verify(repository, never()).delete(any());
    }

    @Test
    void save_whenUsernameAlreadyExists_thenThrowKeyAlreadyExistsException() {
        var dto = new SystemUserCreateRequestDTO();
        dto.setUsername("elcio");
        dto.setPassword("elcio");
        when(repository.save(any())).thenThrow(new DuplicateKeyException("dup"));
        assertThatThrownBy(() -> service.save(dto)).isInstanceOf(KeyAlreadyExistsException.class);
    }

    @Test
    void getLoggedUser() {
    }
}