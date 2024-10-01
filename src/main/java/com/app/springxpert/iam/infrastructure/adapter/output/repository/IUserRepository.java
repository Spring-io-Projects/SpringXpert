package com.app.springxpert.iam.infrastructure.adapter.output.repository;

import com.app.springxpert.iam.domain.model.aggregate.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);

    @Modifying
    @Query("UPDATE UserEntity u SET u.password = ?1 WHERE u.id = ?2")
    void changePassword(@Param("password") String password, @Param("userId") Long userId);

    Optional<UserEntity> findUserEntityByEmail(String email);

    boolean existsByEmailAndUsername(String email, String username);
}