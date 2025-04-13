package com.universidad.tecno.api_gestion_accesorios.services;

import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.entities.Permission;

public interface PermissionService {
    List<Permission> findAll();
    Optional<Permission> findById(Long id);
    Optional<Permission> update(Long id, Permission permission);
    Permission save(Permission permission);
    boolean deleteById(Long id); 
}
