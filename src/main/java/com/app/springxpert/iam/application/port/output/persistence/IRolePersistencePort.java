package com.app.springxpert.iam.application.port.output.persistence;

import com.app.springxpert.iam.domain.model.entity.RoleEntity;
import com.app.springxpert.iam.domain.model.valueobject.RoleEnum;
import java.util.List;
import java.util.Set;

public interface IRolePersistencePort {
    Set<RoleEntity> findRoleEntitiesByRoleNameIn(List<RoleEnum> roleList);
}