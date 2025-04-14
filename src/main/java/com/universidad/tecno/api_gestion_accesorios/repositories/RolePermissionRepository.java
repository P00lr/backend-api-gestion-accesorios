package com.universidad.tecno.api_gestion_accesorios.repositories;

import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.Permission;
import com.universidad.tecno.api_gestion_accesorios.entities.Role;
import com.universidad.tecno.api_gestion_accesorios.entities.RolePermission;

public interface RolePermissionRepository extends CrudRepository<RolePermission, Long>{
    boolean existsByRoleAndPermission(Role role, Permission permission);
}
