package com.universidad.tecno.api_gestion_accesorios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.universidad.tecno.api_gestion_accesorios.entities.Permission;
import com.universidad.tecno.api_gestion_accesorios.entities.Role;
import com.universidad.tecno.api_gestion_accesorios.entities.RolePermission;

public interface RolePermissionRepository extends CrudRepository<RolePermission, Long> {
    boolean existsByRoleAndPermission(Role role, Permission permission);

    @Modifying
    @Query("DELETE FROM RolePermission rp WHERE rp.role = :role")
    void deleteByRole(@Param("role") Role role);
    
    List<RolePermission> findByRoleId(Long roleId);
    List<RolePermission> findByPermissionIdIn(List<Long> permissionIds);

}
