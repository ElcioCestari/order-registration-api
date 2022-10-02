package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.domains.mappers.SystemUserMapper;
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
        return mapper.toDtoResponseList(userRepository.findAll());
    }

    @Override
    public SystemUserResponseDTO findById(String id) {
        return this.mapper.toDtoResponse(this.userRepository.findById(id).orElseThrow());
    }

    @Override
    public SystemUserResponseDTO save(SystemUserRequestDTO dto) {
        try {
            var entity = this.mapper.toEntity(dto);
            SystemUser save = this.userRepository.save(entity);
            return this.mapper.toDtoResponse(save);
        } catch (DuplicateKeyException e) {
            throw new KeyAlreadyExistsException(e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    @Override
    public SystemUserResponseDTO update(SystemUserRequestDTO dto, String id) {
        throw new UnsupportedOperationException("Sorry! But this was not implemented yet.");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Sorry! But this was not implemented yet.");
    }

    public SystemUserResponseDTO getLoggedUser() {
        try {
            return mapper.toDtoResponse(
                    (SystemUser) this.userDetailService
                            .getAuthentication()
                            .getPrincipal());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException();
        }

    }
}