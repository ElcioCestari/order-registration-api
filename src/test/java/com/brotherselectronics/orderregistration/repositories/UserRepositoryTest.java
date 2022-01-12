package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.fakers.UserFaker;
import com.brotherselectronics.orderregistration.domains.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

@DataMongoTest
class UserRepositoryTest {

    @Autowired private UserRepository userRepository;
    private UserFaker fakeUser;

    @BeforeEach
    void setUp() {
        fakeUser = new UserFaker();
    }

    @Test
    void findByLogin() {
        Optional<User> user = userRepository.findByLogin(fakeUser.getEntity().getLogin());
        Assertions.assertThat(user).isNotNull();
    }

    @Test
    void save() {
//        User save = userRepository.save(fakeUser.getEntity());
//        Assertions.assertThat(save).isNotNull();
    }
}