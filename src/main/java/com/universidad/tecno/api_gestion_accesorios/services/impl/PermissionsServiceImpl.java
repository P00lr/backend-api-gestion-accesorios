package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universidad.tecno.api_gestion_accesorios.entities.Permission;
import com.universidad.tecno.api_gestion_accesorios.entities.Role;
import com.universidad.tecno.api_gestion_accesorios.entities.RolePermission;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.entities.UserRolePermission;
import com.universidad.tecno.api_gestion_accesorios.repositories.PermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.RolePermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.RoleRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRolePermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.PermissionService;

@Service
public class PermissionsServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolePermissionRepository userRolePermissionRepository;

    @Override
    public Page<Permission> paginateAll(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

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
        return permissionRepository.findById(id).map(existingPermission -> {
            if (permission.getName() != null) {
                existingPermission.setName(permission.getName());
            }
            return permissionRepository.save(existingPermission);
        });
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Permission> permissionOp = permissionRepository.findById(id);
        if (permissionOp.isPresent()) {
            permissionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
@Override
public void assignPermissionsToRole(Long roleId, List<Long> permissionIds) {
    Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RuntimeException("No se encontró el rol"));

    // 1. Obtener las asignaciones actuales del rol
    List<RolePermission> existingRolePermissions = rolePermissionRepository.findByRoleId(roleId);

    // 2. Filtrar RolePermission que se deben mantener (los que siguen en permissionIds)
    List<RolePermission> toKeep = existingRolePermissions.stream()
            .filter(rp -> permissionIds != null && permissionIds.contains(rp.getPermission().getId()))
            .collect(Collectors.toList());

    // 3. RolePermission que se deben eliminar (los que no están en permissionIds)
    List<RolePermission> toRemove = existingRolePermissions.stream()
            .filter(rp -> permissionIds == null || !permissionIds.contains(rp.getPermission().getId()))
            .collect(Collectors.toList());

    if (!toRemove.isEmpty()) {
        // 4. Eliminar UserRolePermission relacionados con RolePermission a eliminar
        userRolePermissionRepository.deleteByRolePermissionIn(toRemove);

        // 5. Eliminar solo los RolePermission que ya no se asignan al rol
        rolePermissionRepository.deleteAll(toRemove);
    }

    if (permissionIds == null || permissionIds.isEmpty()) {
        // Si no hay permisos nuevos, termina aquí (rol sin permisos)
        return;
    }

    // 6. Obtener permisos nuevos (los que no estaban antes)
    List<Long> existingPermissionIds = toKeep.stream()
            .map(rp -> rp.getPermission().getId())
            .collect(Collectors.toList());

    List<Long> newPermissionIds = permissionIds.stream()
            .filter(id -> !existingPermissionIds.contains(id))
            .collect(Collectors.toList());

    if (!newPermissionIds.isEmpty()) {
        List<Permission> newPermissions = (List<Permission>) permissionRepository.findAllById(newPermissionIds);

        // 7. Crear y guardar solo los nuevos RolePermission
        List<RolePermission> newRolePermissions = newPermissions.stream()
                .map(permission -> new RolePermission(role, permission))
                .collect(Collectors.toList());

        rolePermissionRepository.saveAll(newRolePermissions);
    }
}


    @Transactional
    @Override
    public void assignPermissionsToUser(Long userId, List<Long> permissionIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario"));

        //Eliminar TODAS las asignaciones actuales del usuario
        userRolePermissionRepository.deleteByUserId(userId);

        if (permissionIds == null || permissionIds.isEmpty())
            return;

        //Obtener los RolePermission relacionados con esos permisos
        List<RolePermission> rolePermissions = rolePermissionRepository.findByPermissionIdIn(permissionIds);

        //Crear y guardar los nuevos UserRolePermission
        List<UserRolePermission> newUserPermissions = rolePermissions.stream()
                .map(rp -> new UserRolePermission(user, rp))
                .collect(Collectors.toList());

        userRolePermissionRepository.saveAll(newUserPermissions);
    }

}
