package com.universidad.tecno.api_gestion_accesorios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.universidad.tecno.api_gestion_accesorios.entities.RolePermission;
import com.universidad.tecno.api_gestion_accesorios.entities.UserRolePermission;

public interface UserRolePermissionRepository extends CrudRepository<UserRolePermission, Long> {
    List<UserRolePermission> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserRolePermission urp WHERE urp.rolePermission IN :rolePermissions")
    void deleteByRolePermissionIn(@Param("rolePermissions") List<RolePermission> rolePermissions);

    @Modifying
    @Query("DELETE FROM UserRolePermission urp WHERE urp.user.id = :userId")
    void deleteByUserId(Long userId);
}
