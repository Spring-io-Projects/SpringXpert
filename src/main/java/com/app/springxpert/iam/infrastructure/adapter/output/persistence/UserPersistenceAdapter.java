package com.app.springxpert.iam.infrastructure.adapter.output.persistence;

import com.app.springxpert.iam.application.port.output.persistence.IUserPersistencePort;
import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import com.app.springxpert.iam.infrastructure.adapter.output.repository.IUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPersistenceAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;

    @Override
    public Optional<UserEntity> findUserEntityByUsername(String username) {
        return userRepository.findUserEntityByUsername(username);
    }

    @Override
    public UserEntity signUp(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public void changePassword(String password, Long userId) {
        userRepository.changePassword(password, userId);
    }

    @Override
    public Optional<UserEntity> findUserEntityByEmail(String email) {
        return userRepository.findUserEntityByEmail(email);
    }

    @Override
    public boolean existsByEmailAndUsername(String email, String username) {
        return userRepository.existsByEmailAndUsername(email, username);
    }
}
