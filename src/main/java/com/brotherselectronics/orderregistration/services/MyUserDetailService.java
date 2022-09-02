package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByUsername(login)
                .orElseThrow(() -> new NotFoundException("Not found a User with login: " + login));
    }
}
