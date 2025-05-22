package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.universidad.tecno.api_gestion_accesorios.entities.Permission;

public interface PermissionService {        

    Page<Permission> paginateAll(Pageable pageable);
    List<Permission> findAll();
    Optional<Permission> findById(Long id);
    Optional<Permission> update(Long id, Permission permission);
    Permission save(Permission permission);
    boolean deleteById(Long id); 

    void assignPermissionsToRole(Long roleId, List<Long> permissionIds);
}
