package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.domains.mappers.SystemUserMapper;
import com.brotherselectronics.orderregistration.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemUserService implements IBaseService<SystemUserRequestDTO, SystemUserResponseDTO, SystemUser> {

    private final UserRepository userRepository;
    private final SystemUserMapper mapper;

    @Autowired
    public SystemUserService(UserRepository userRepository, SystemUserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

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
        var entity = this.mapper.toEntity(dto);
        SystemUser save = this.userRepository.save(entity);
        return this.mapper.toDtoResponse(save);
    }

    @Override
    public SystemUserResponseDTO update(SystemUserRequestDTO dto, String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}