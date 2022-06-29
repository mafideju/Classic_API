package org.mafideju.API_Kargopolov.repository;

import org.mafideju.API_Kargopolov.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    // DEFAULT IMPLEMENTATIONS ARE BEING USED
    // CREATE HERE YOUR CUSTOM DEFINITIONS:

    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
}
