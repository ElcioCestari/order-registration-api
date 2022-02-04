package com.brotherselectronics.orderregistration.domains.mappers;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SystemUserMapper extends IBaseMapper<SystemUser, SystemUserRequestDTO, SystemUserResponseDTO>{

    @Override
    default  SystemUserResponseDTO toDtoResponse(SystemUser entity) {
        if(entity == null) return null;
        return SystemUserResponseDTO.builder()
                .username(entity.getUsername())
                .credentialsNonExpired(entity.isCredentialsNonExpired())
                .accountNonExpired(entity.isAccountNonExpired())
                .accountNonLocked(entity.isAccountNonLocked())
                .authorities((Set<GrantedAuthority>) entity.getAuthorities())
                .enabled(entity.isEnabled())
                .build();
    }
}
