package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.exceptions.InternalServerError;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<SystemUser> user;
        try {
            user = userRepository.findByUsername(login);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerError();
        }
        return user.orElseThrow(() -> new NotFoundException("Not found a User with login: " + login));
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
