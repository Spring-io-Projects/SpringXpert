package com.app.springxpert.iam.infrastructure.adapter.output.repository;

import com.app.springxpert.iam.domain.model.entity.RoleEntity;
import com.app.springxpert.iam.domain.model.valueobject.RoleEnum;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    Set<RoleEntity> findRoleEntitiesByRoleNameIn(List<RoleEnum> roleList);
}
