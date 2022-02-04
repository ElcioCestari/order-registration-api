package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.domains.mappers.SystemUserMapper;
import com.brotherselectronics.orderregistration.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public SystemUserResponseDTO save(SystemUserRequestDTO dto) {
        return null;
    }

    @Override
    public SystemUserResponseDTO update(SystemUserRequestDTO dto, String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}