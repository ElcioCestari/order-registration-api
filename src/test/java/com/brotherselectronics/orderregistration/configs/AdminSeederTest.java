package com.brotherselectronics.orderregistration.configs;

import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.domains.enums.Role;
import com.brotherselectronics.orderregistration.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AdminSeederTest {

    private final PasswordEncoder encoder = createDelegatingPasswordEncoder();

    @Mock
    private UserRepository userRepository;

    @Test
    void run_whenAdminDoesNotExist_thenCreatesAdminWithEncodedPassword() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());

        new AdminSeeder(userRepository, encoder, "admin", "s3cret").run(null);

        ArgumentCaptor<SystemUser> saved = ArgumentCaptor.forClass(SystemUser.class);
        verify(userRepository).save(saved.capture());
        assertThat(saved.getValue().getUsername()).isEqualTo("admin");
        assertThat(saved.getValue().getPassword()).startsWith("{bcrypt}");
        assertThat(encoder.matches("s3cret", saved.getValue().getPassword())).isTrue();
        assertThat(saved.getValue().getAuthorities())
                .extracting("authority").containsExactly(Role.ADMIN.getAuthority());
    }

    @Test
    void run_whenUsernameAlreadyExists_thenDoesNotTouchIt() {
        var existing = SystemUser.Factory.customer("admin", "{bcrypt}current");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(existing));

        new AdminSeeder(userRepository, encoder, "admin", "s3cret").run(null);

        verify(userRepository, never()).save(any());
    }

    @Test
    void run_whenCredentialsAreNotConfigured_thenDoesNothing() {
        new AdminSeeder(userRepository, encoder, "", "").run(null);
        new AdminSeeder(userRepository, encoder, "admin", "").run(null);

        verify(userRepository, never()).findByUsername(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void run_whenAnotherInstanceCreatedItConcurrently_thenDoesNotFail() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenThrow(new DuplicateKeyException("dup"));

        assertThatCode(() -> new AdminSeeder(userRepository, encoder, "admin", "s3cret").run(null))
                .doesNotThrowAnyException();
    }
}
