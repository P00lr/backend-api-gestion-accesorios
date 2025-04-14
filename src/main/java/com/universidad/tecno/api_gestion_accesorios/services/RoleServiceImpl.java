package com.universidad.tecno.api_gestion_accesorios.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.dto.PermissionDto;
import com.universidad.tecno.api_gestion_accesorios.dto.RoleWithPermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Permission;
import com.universidad.tecno.api_gestion_accesorios.entities.Role;
import com.universidad.tecno.api_gestion_accesorios.entities.RolePermission;
import com.universidad.tecno.api_gestion_accesorios.repositories.PermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.RolePermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.RoleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    public List<RoleWithPermissionsDto> getRolesWithPermissions() {
        List<Role> roles = (List<Role>) roleRepository.findAll();

        return roles.stream().map(role -> {
            List<String> permisos = role.getRolePermissions().stream()
                    .map(rp -> rp.getPermission().getName()) // Extraemos el nombre del permiso
                    .collect(Collectors.toList());

            return new RoleWithPermissionsDto(role.getId(), role.getName(), permisos);
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> update(Long id, Role role) {
        return roleRepository.findById(id).map(existingRole -> {
            if (role.getName() != null) {
                existingRole.setName(role.getName());
            }
            return roleRepository.save(existingRole);
        });
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Role> roleOp = roleRepository.findById(id);
        if (roleOp.isPresent()) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void assignPermissionsToRole(Long roleId, List<Long> permissionIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        // convierte en una lista de permisos
        List<Permission> permissions = StreamSupport
                .stream(permissionRepository.findAllById(permissionIds).spliterator(), false)
                .collect(Collectors.toList());

        for (Permission permission : permissions) {
            // Verificar si ya existe para no duplicar
            boolean alreadyExists = rolePermissionRepository.existsByRoleAndPermission(role, permission);
            if (alreadyExists)
                continue;

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRole(role);
            rolePermission.setPermission(permission);

            rolePermissionRepository.save(rolePermission);
        }
    }

}
