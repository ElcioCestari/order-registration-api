package com.brotherselectronics.orderregistration.configs;

import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static org.springframework.util.StringUtils.hasText;

/**
 * Creates the first ADMIN user on startup, so a fresh database can be used without
 * inserting users by hand. Credentials come from {@code app.admin.username} and
 * {@code app.admin.password} (env vars ADMIN_USERNAME / ADMIN_PASSWORD).
 *
 * <p>It never changes an existing user: if the username already exists, nothing is done,
 * so passwords or roles changed through the API are not overwritten on restart.
 */
@Slf4j
@Component
public class AdminSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final String username;
    private final String password;

    public AdminSeeder(UserRepository userRepository,
                       PasswordEncoder encoder,
                       @Value("${app.admin.username:}") String username,
                       @Value("${app.admin.password:}") String password) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!hasText(username) || !hasText(password)) {
            log.warn("Initial admin not created: ADMIN_USERNAME and ADMIN_PASSWORD are not set");
            return;
        }
        if (userRepository.findByUsername(username).isPresent()) {
            log.info("Initial admin not created: user '{}' already exists", username);
            return;
        }
        try {
            userRepository.save(SystemUser.Factory.buildAdmin(username, encoder.encode(password)));
            log.info("Initial admin '{}' created", username);
        } catch (DuplicateKeyException e) {
            // another instance created it at the same time
            log.info("Initial admin not created: user '{}' already exists", username);
        }
    }
}
