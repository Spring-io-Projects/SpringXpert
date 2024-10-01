package com.app.springxpert.iam.application.port.output.persistence;

import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import java.util.Optional;

public interface IUserPersistencePort {
    Optional<UserEntity> findUserEntityByUsername(String username);
    UserEntity signUp(UserEntity userEntity);
    void changePassword(String password, Long userId);
    Optional<UserEntity> findUserEntityByEmail(String email);
    boolean existsByEmailAndUsername(String email, String username);
}