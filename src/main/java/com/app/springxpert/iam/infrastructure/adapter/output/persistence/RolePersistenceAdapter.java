package com.app.springxpert.iam.infrastructure.adapter.output.persistence;

import com.app.springxpert.iam.application.port.output.persistence.IRolePersistencePort;
import com.app.springxpert.iam.domain.model.entity.RoleEntity;
import com.app.springxpert.iam.domain.model.valueobject.RoleEnum;
import com.app.springxpert.iam.infrastructure.adapter.output.repository.IRoleRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolePersistenceAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;

    @Override
    public Set<RoleEntity> findRoleEntitiesByRoleNameIn(List<RoleEnum> roleList) {
        return roleRepository.findRoleEntitiesByRoleNameIn(roleList);
    }
}