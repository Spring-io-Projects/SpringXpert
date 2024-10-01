package com.app.springxpert.iam.infrastructure.adapter.output.repository;

import com.app.springxpert.iam.domain.model.entity.VerificationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IVerificationRepository extends JpaRepository<VerificationEntity, Long> {
    Optional<VerificationEntity> findVerificationEntityByCode(String code);

    @Query("SELECT v FROM VerificationEntity v WHERE v.code = ?1 AND v.user.email = ?2")
    Optional<VerificationEntity> findVerificationEntityByCodeAndEmail(@Param("code") String code, @Param("email") String email);
}
