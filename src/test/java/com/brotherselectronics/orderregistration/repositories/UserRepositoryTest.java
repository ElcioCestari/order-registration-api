package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.fakers.UserFaker;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private SystemUser fakeUser;

    @BeforeEach
    void setUp() {
        fakeUser = new UserFaker().getEntity();
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(fakeUser);
    }

    @Test
    void findByLogin() {
        Optional<SystemUser> user = userRepository.findByUsername(fakeUser.getUsername());
        assertThat(user).isNotNull();
    }

    @Test
    void save() {
        assertThat(userRepository.save(fakeUser)).isNotNull();
    }

    @Test
    void save_whenAlreadyExistsAnUserWithTheSameUserName() {
        userRepository.insert(fakeUser);
        var invalidUser = SystemUser.Factory.simpleUser(fakeUser.getUsername(), "");
        assertThrows(DuplicateKeyException.class, () -> userRepository.insert(invalidUser));

    }
}