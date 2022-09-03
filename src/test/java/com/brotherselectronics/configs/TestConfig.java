package com.brotherselectronics.configs;

import com.brotherselectronics.orderregistration.repositories.UserRepository;
import com.brotherselectronics.orderregistration.services.MyUserDetailService;
import org.mockito.Mock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * This class it's a configuration to MyUserDetailService
 * it was deprecated because it's not more necessarily,
 * was not removed yet just to serve like a reference if necessary to do another configuration
 */
@Deprecated(since = "2022-09-03")
@TestConfiguration
public class TestConfig {
    @Mock
    private UserRepository repository;

    @Bean
    public MyUserDetailService myUserDetailService() {
        return new MyUserDetailService(repository);
    }
}
