package com.brotherselectronics.orderregistration.domains.mappers;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserCreateRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserUpdateRequestDTO;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.domains.enums.Role;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Mapper(componentModel = "spring")
public interface SystemUserMapper extends IBaseMapper<SystemUser, SystemUserRequestDTO, SystemUserResponseDTO> {

    @Override
    default SystemUserResponseDTO toDtoResponse(@NonNull SystemUser entity) {
        return SystemUserResponseDTO.builder()
                .username(entity.getUsername())
                .id(entity.getId())
                .credentialsNonExpired(entity.isCredentialsNonExpired())
                .accountNonExpired(entity.isAccountNonExpired())
                .accountNonLocked(entity.isAccountNonLocked())
                .authorities(new HashSet<>(
                        entity.getAuthorities()
                                .stream()
                                .map(SimpleGrantedAuthority::getAuthority)
                                .collect(toSet())))
                .enabled(entity.isEnabled())
                .build();
    }

    @Override
    @Mapping(target = "id", ignore = true)
    SystemUser toEntity(SystemUserRequestDTO dtoRequest);

    @Mapping(target = "id", ignore = true)
    SystemUser toEntity(SystemUserCreateRequestDTO dtoRequest);

    default SystemUser toEntity(@NonNull SystemUserUpdateRequestDTO dtoRequest) {
        Set<SimpleGrantedAuthority> authorities = dtoRequest.getAuthorities().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(toSet());
        return new SystemUser(null, "", dtoRequest.getPassword(), authorities);
    }

    default Set<Role> map(@NonNull Collection<SimpleGrantedAuthority> value) {
        return value.stream().map(v -> Role.valueOf(v.getAuthority())).collect(toSet());
    }
}
