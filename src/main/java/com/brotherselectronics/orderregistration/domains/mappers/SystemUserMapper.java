package com.brotherselectronics.orderregistration.domains.mappers;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;

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
}
