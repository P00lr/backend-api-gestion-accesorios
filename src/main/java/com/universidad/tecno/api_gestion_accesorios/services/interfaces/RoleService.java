package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.universidad.tecno.api_gestion_accesorios.dto.role.RoleWithPermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.dto.user.UserWithRolesAndPermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Role;

public interface RoleService {

    Page<RoleWithPermissionsDto> paginateAllRoleWithPermissions(Pageable pageable);
    Page<Role> paginateAll(Pageable pageable);
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Role save(Role role);
    Optional<Role> update(Long id, Role role);
    boolean deleteById(Long id);

    List<RoleWithPermissionsDto> getRolesWithPermissions();

    List<UserWithRolesAndPermissionsDto> getUsersWithRolesAndPermissions();
}
