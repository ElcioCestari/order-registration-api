package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.fakers.UserFaker;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MyUserDetailServiceTest {

    @InjectMocks
    private MyUserDetailService myUserDetailService;

    @Mock
    private UserRepository userRepository;

    private SystemUser userFake;

    @BeforeEach
    public void setup() {
        userFake = new UserFaker().getEntity();
    }

    @Test
    void loadUserByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(userFake));
        UserDetails user = myUserDetailService.loadUserByUsername("f12312312");
        Assertions.assertThat(user).isNotNull();
    }
}