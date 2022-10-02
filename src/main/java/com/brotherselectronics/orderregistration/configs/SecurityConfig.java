package com.brotherselectronics.orderregistration.configs;

import com.brotherselectronics.orderregistration.services.MyUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.brotherselectronics.orderregistration.domains.enums.Role.ADMIN;
import static com.brotherselectronics.orderregistration.domains.enums.Role.USER;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailService userDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/home", "/")
                .permitAll()
                .antMatchers(POST, "/users/login").hasAnyRole(USER.getAuthority(), ADMIN.getAuthority())
                .antMatchers(GET, "/**").hasAnyRole(USER.getAuthority(), ADMIN.getAuthority())
                .antMatchers(POST, "/**").hasAnyRole(ADMIN.getAuthority())
                .antMatchers(PUT, "/**").hasAnyRole(ADMIN.getAuthority())
                .antMatchers(DELETE, "/**").hasAnyRole(ADMIN.getAuthority())
                .anyRequest()
                .authenticated()
//                .permitAll()
                .and()
                .httpBasic()
                .and()
                .requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(createDelegatingPasswordEncoder());
    }
}
