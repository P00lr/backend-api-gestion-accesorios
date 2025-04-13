package com.universidad.tecno.api_gestion_accesorios.repositories;

import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.Permission;

public interface PermissionRepository extends CrudRepository<Permission, Long>{
    
}
