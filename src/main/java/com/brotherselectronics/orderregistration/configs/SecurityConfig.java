package com.brotherselectronics.orderregistration.configs;

import com.brotherselectronics.orderregistration.services.MyUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailService userDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .httpBasic()
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrf().disable()
//                .and()
                .authorizeRequests()
//                .antMatchers("/home", "/")
//                .permitAll()
//                .antMatchers(GET, "/**").hasAnyRole("USER","ADMIN")
//                .antMatchers(POST, "/**").hasAnyRole("ADMIN")
//                .antMatchers(PUT, "/**").hasAnyRole("ADMIN")
//                .antMatchers(DELETE, "/**").hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated()
//                .denyAll()
//                .permitAll()
                .and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        auth.inMemoryAuthentication()
//                .withUser("elcio")
//                .password(encoder.encode("elcio"))
//                .roles("ADMIN")
//                .and()
//                .withUser("maria")
//                .password(encoder.encode("maria"))
//                .roles("USER");

        log.info(encoder.encode("elcio"));

        auth.userDetailsService(userDetailService).passwordEncoder(encoder);
    }
}
