package com.universidad.tecno.api_gestion_accesorios.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.entities.Permission;
import com.universidad.tecno.api_gestion_accesorios.entities.Role;
import com.universidad.tecno.api_gestion_accesorios.entities.RolePermission;
import com.universidad.tecno.api_gestion_accesorios.repositories.PermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.RolePermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.RoleRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class PermissionsServiceImpl implements PermissionService{

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    @Override
    public void assignPermissionsToRole(Long roleId, List<Long> permissionIds) {
        if (roleId == null) {
            throw new IllegalArgumentException("El ID del rol no puede ser null.");
        }

        if (permissionIds == null || permissionIds.isEmpty()) {
            throw new IllegalArgumentException("La lista de IDs de permisos no puede ser null ni vacía.");
        }

        if (permissionIds.contains(null)) {
            throw new IllegalArgumentException("La lista de IDs de permisos no puede contener valores null.");
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con ID: " + roleId));

        List<Permission> permissions = StreamSupport
                .stream(permissionRepository.findAllById(permissionIds).spliterator(), false)
                .collect(Collectors.toList());

        // Validar si realmente se encontraron todos los permisos
        if (permissions.size() != permissionIds.size()) {
            throw new EntityNotFoundException("No se encontraron todos los permisos con los IDs proporcionados.");
        }

        for (Permission permission : permissions) {
            if (!rolePermissionRepository.existsByRoleAndPermission(role, permission)) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRole(role);
                rolePermission.setPermission(permission);
                rolePermissionRepository.save(rolePermission);
            }
        }
    }

}
