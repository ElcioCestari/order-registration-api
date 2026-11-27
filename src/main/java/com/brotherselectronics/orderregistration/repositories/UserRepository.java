package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<SystemUser, String> {
    @Override
    default Class<SystemUser> getEntityClass() {
        return SystemUser.class;
    }

    Optional<SystemUser> findByUsername(String login);
}