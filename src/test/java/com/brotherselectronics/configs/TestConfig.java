package com.brotherselectronics.configs;

import com.brotherselectronics.orderregistration.repositories.UserRepository;
import com.brotherselectronics.orderregistration.services.MyUserDetailService;
import org.mockito.Mock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Mock
    private UserRepository repository;

    @Bean
    public MyUserDetailService myUserDetailService() {
        return new MyUserDetailService(repository);
    }
}
