package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserCreateRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserUpdateRequestDTO;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.domains.mappers.SystemUserMapper;
import com.brotherselectronics.orderregistration.exceptions.InternalApiException;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemUserService implements IBaseService<SystemUserRequestDTO, SystemUserResponseDTO, SystemUser> {

    private final UserRepository userRepository;
    private final SystemUserMapper mapper;
    private final MyUserDetailService userDetailService;

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
            return this.mapper
                    .toDtoResponse(this.userRepository.findById(id)
                    .orElseThrow(NotFoundException::new));
        } catch (NotFoundException e) {
            throw e;
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalApiException();
        }
    }

    @Override
    public SystemUserResponseDTO save(SystemUserRequestDTO dto) {
        try {
            var entity = this.mapper.toEntity((SystemUserCreateRequestDTO) dto);
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
            var systemUser = userRepository
                    .findById(id)
                    .orElseThrow(NotFoundException::new);
            merge(mapper.toEntity((SystemUserUpdateRequestDTO) dto), systemUser);
            userRepository.save(systemUser);
            return mapper.toDtoResponse(systemUser);
        } catch (NotFoundException e) {
            log.error(e.getMessage(), e);
            throw new InternalApiException();
        }
    }

    @Override
    public void delete(String id) {
        try {
            userRepository
                    .findById(id)
                    .ifPresentOrElse(userRepository::delete, NotFoundException::new);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalApiException();
        }
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