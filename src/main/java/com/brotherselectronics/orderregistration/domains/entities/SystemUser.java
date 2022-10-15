package com.brotherselectronics.orderregistration.domains.entities;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;

import static com.brotherselectronics.orderregistration.domains.enums.Role.ADMIN;
import static com.brotherselectronics.orderregistration.domains.enums.Role.USER;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Objects.isNull;
import static java.util.Set.of;
import static java.util.UUID.randomUUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document("Users")
public class SystemUser extends BaseEntityImp implements BaseEntity, UserDetails {

    @Indexed(unique = true)
    @NonNull
    @NotBlank
    private final String username;

    @NonNull
    @NotBlank
    private String password;

    @NonNull
    private Collection<SimpleGrantedAuthority> authorities;

    public SystemUser(String id,
                      @NonNull String username,
                      @NonNull String password,
                      Collection<SimpleGrantedAuthority> authorities) {
        super(id);
        this.username = username;
        this.password = password;
        if (isNull(authorities) || authorities.isEmpty()) {
            this.authorities = new ArrayList<>();
            this.authorities.add(new SimpleGrantedAuthority(USER.getAuthority()));
        } else {
            this.authorities = new ArrayList<>(authorities);
        }
    }

    @Override
    public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
        return unmodifiableCollection(this.authorities);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setAuthorities(@NonNull Collection<? extends SimpleGrantedAuthority> authorities) throws IllegalAccessException {
        if (authorities.isEmpty()) {
            throw new IllegalAccessException("invalid authorities");
        }
        this.authorities.clear();
        this.authorities.addAll(authorities);
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