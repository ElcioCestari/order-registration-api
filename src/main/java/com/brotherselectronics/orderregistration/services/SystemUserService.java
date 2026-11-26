package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserCreateRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.domains.mappers.SystemUserMapper;
import com.brotherselectronics.orderregistration.exceptions.InternalApiException;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toSet;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemUserService implements BaseService<SystemUserRequestDTO, SystemUserResponseDTO, SystemUser> {

    private final UserRepository userRepository;
    private final SystemUserMapper mapper;
    private final MyUserDetailService userDetailService;
    private final PasswordEncoder encoder;

    @Override
    public List<SystemUserResponseDTO> findAll() {
        try {
            return mapper.toDtoResponseList(userRepository.findAll());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalApiException();
        }
    }

    @Override
    public SystemUserResponseDTO findById(String id) {
        try {
            return this.mapper.toDtoResponse(getUserOrThrowNotFoundException(id));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalApiException();
        }
    }

    @Override
    public SystemUserResponseDTO save(SystemUserRequestDTO dto) {
        try {
            var entity = this.mapper.toEntity((SystemUserCreateRequestDTO) dto);
            entity.setPassword(encoder.encode(entity.getPassword()));
            SystemUser save = this.userRepository.save(entity);
            return this.mapper.toDtoResponse(save);
        } catch (DuplicateKeyException e) {
            throw new KeyAlreadyExistsException(e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalApiException();
        }
    }

    @Override
    public SystemUserResponseDTO update(SystemUserRequestDTO dto, String id) {
        try {
            var systemUser = getUserOrThrowNotFoundException(id);
            if (hasText(dto.getPassword())) {
                systemUser.setPassword(encoder.encode(dto.getPassword()));
            }
            if (!isEmpty(dto.getAuthorities())) {
                systemUser.setAuthorities(dto.getAuthorities().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                        .collect(toSet()));
            }
            userRepository.save(systemUser);
            return mapper.toDtoResponse(systemUser);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalApiException();
        }
    }

    @Override
    public void delete(String id) {
        try {
            userRepository.delete(getUserOrThrowNotFoundException(id));
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalApiException();
        }
    }

    private SystemUser getUserOrThrowNotFoundException(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public SystemUserResponseDTO getLoggedUser() {
        try {
            return mapper.toDtoResponse(
                    (SystemUser) this.userDetailService
                            .getAuthentication()
                            .getPrincipal());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalApiException();
        }

    }
}