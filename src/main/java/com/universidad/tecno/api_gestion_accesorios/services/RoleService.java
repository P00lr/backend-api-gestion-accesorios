package com.universidad.tecno.api_gestion_accesorios.services;

import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.dto.RoleWithPermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Role;

public interface RoleService {

    List<Role> findAll();
    Optional<Role> findById(Long id);
    Role save(Role role);
    Optional<Role> update(Long id, Role role);
    boolean deleteById(Long id);

    void assignPermissionsToRole(Long roleId, List<Long> permissionIds);
    List<RoleWithPermissionsDto> getRolesWithPermissions();
}
