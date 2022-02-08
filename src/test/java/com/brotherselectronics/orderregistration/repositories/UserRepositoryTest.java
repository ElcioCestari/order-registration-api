package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.fakers.UserFaker;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

@DataMongoTest
class UserRepositoryTest {

    @Autowired private UserRepository userRepository;
    private SystemUser fakeUser;

    @BeforeEach
    void setUp() {
        fakeUser = new UserFaker().getEntity();
    }

    @Test
    void findByLogin() {
        Optional<SystemUser> user = userRepository.findByUsername(fakeUser.getUsername());
        Assertions.assertThat(user).isNotNull();
    }

    @Test
    void save() {
        SystemUser user = userRepository.save(fakeUser);
        Assertions.assertThat(user).isNotNull();
    }
}