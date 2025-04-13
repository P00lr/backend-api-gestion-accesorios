package com.universidad.tecno.api_gestion_accesorios.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.entities.Permission;
import com.universidad.tecno.api_gestion_accesorios.repositories.PermissionRepository;


@Service
public class PermissionsServiceImpl implements PermissionService{

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return (List<Permission>) permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }
    @Override
    public Optional<Permission> update(Long id, Permission permission) {
        return  permissionRepository.findById(id).map(existingPermission -> {
            if(permission.getName() != null) {existingPermission.setName(permission.getName());}
            return permissionRepository.save(existingPermission);
        });
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Permission> permissionOp = permissionRepository.findById(id);
        if(permissionOp.isPresent()) {
            permissionRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
