package com.universidad.tecno.api_gestion_accesorios.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.Permission;

public interface PermissionRepository extends CrudRepository<Permission, Long>{
    Page<Permission> findAll(Pageable pageable);
    List<Permission> findAllById(Long id);
    
    
}
