package com.brotherselectronics.orderregistration.domains.mappers;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashSet;

@Mapper(componentModel = "spring")
public interface SystemUserMapper extends IBaseMapper<SystemUser, SystemUserRequestDTO, SystemUserResponseDTO> {

    @Override
    default SystemUserResponseDTO toDtoResponse(@NonNull SystemUser entity) {
        return SystemUserResponseDTO.builder()
                .username(entity.getUsername())
                .credentialsNonExpired(entity.isCredentialsNonExpired())
                .accountNonExpired(entity.isAccountNonExpired())
                .accountNonLocked(entity.isAccountNonLocked())
                .authorities(new HashSet<>(entity.getAuthorities()))
                .enabled(entity.isEnabled())
                .build();
    }

    @Override
    @Mapping(target = "authorities", ignore = true)
    SystemUser toEntity(SystemUserRequestDTO dtoRequest);
}
