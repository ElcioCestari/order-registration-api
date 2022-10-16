package com.brotherselectronics.orderregistration.domains.mappers;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserUpdateRequestDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.brotherselectronics.orderregistration.testsutils.GenericFactory.buildObjectOfAnyType;
import static org.junit.jupiter.api.Assertions.fail;

class SystemUserMapperTest {

    private final SystemUserMapper mapper = Mappers.getMapper(SystemUserMapper.class);

    @Test
    void toEntity_givenAUserToBeUpdated_thenDontThrowException() {
        try {
            mapper.toEntity(buildObjectOfAnyType(SystemUserUpdateRequestDTO.class));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}