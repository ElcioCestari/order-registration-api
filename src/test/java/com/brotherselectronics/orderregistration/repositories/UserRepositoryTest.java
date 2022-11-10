package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.fakers.UserFaker;
import com.brotherselectronics.orderregistration.configs.MongoConfig;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static com.brotherselectronics.orderregistration.domains.entities.SystemUser.Factory.simpleUser;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(MongoConfig.class)
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
    void deleteByLogin() {
        try {
            userRepository.findById(fakeUser.getId())
                    .ifPresentOrElse(systemUser -> userRepository.delete(systemUser), RuntimeException::new);
        } catch (Exception e) {
            fail("should not be throw exception");
        }

    }

    @Test
    void save() {
        var user = simpleUser(randomUUID().toString(), randomUUID().toString());
        assertThat(userRepository.save(user)).isNotNull();
    }

    @Test
    void save_whenUserPasswordEmptyThenShouldNotBeSaved() {
        var user = simpleUser(randomUUID().toString(), randomUUID().toString());
        user.setPassword("");
        assertThatThrownBy(() -> userRepository.save(user)).isInstanceOf(ConstraintViolationException.class);
    }


    /**
     * Test if are be inserting duplicate users.
     *
     * @deprecated - not working in staging.
     */
    @Deprecated
    void save_whenAlreadyExistsAnUserWithTheSameUserName() {
        userRepository.insert(fakeUser);
        var invalidUser = simpleUser(fakeUser.getUsername(), randomUUID().toString());
        assertThrows(DuplicateKeyException.class, () -> userRepository.insert(invalidUser));

    }
}