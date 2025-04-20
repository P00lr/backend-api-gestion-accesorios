package com.universidad.tecno.api_gestion_accesorios.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.UserRolePermission;

public interface UserRolePermissionRepository extends CrudRepository<UserRolePermission, Long> {
    List<UserRolePermission> findByUserId(Long userId);
}
