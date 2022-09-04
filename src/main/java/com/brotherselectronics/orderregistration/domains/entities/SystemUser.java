package com.brotherselectronics.orderregistration.domains.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

import static com.brotherselectronics.orderregistration.domains.enums.Role.ADMIN;
import static com.brotherselectronics.orderregistration.domains.enums.Role.USER;
import static java.util.Set.of;
import static java.util.UUID.randomUUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Document("Users")
@AllArgsConstructor
public class SystemUser implements BaseEntity, UserDetails {

    @Id
    @NonNull
    @NotBlank
    private final String id;

    @Indexed(unique = true)
    @NonNull
    @NotBlank
    private final String username;

    @NonNull
    @NotBlank
    private String password;

    @NonNull
    private Collection<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static final class Factory {
        private Factory() throws IllegalAccessException {
            throw new IllegalAccessException();
        }

        public static SystemUser simpleUser(@NonNull final String username, @NonNull final String password) {
            return new SystemUser(
                    randomUUID().toString(),
                    username,
                    password,
                    of(new SimpleGrantedAuthority(USER.getAuthority())));
        }

        public static SystemUser buildAdmin(@NonNull final String username, @NonNull final String password) {
            return new SystemUser(
                    randomUUID().toString(),
                    username,
                    password,
                    of(new SimpleGrantedAuthority(ADMIN.getAuthority())));
        }
    }
}