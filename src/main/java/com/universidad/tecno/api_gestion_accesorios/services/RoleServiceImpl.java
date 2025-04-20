package com.universidad.tecno.api_gestion_accesorios.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.dto.role.RolePermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.dto.role.RoleWithPermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.dto.user.UserWithRolesAndPermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Role;
import com.universidad.tecno.api_gestion_accesorios.entities.RolePermission;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.entities.UserRolePermission;
import com.universidad.tecno.api_gestion_accesorios.repositories.RolePermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.RoleRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRolePermissionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private UserRolePermissionRepository userRolePermissionRepository;

    @Autowired
    private UserRepository userRepository;

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
    public List<UserWithRolesAndPermissionsDto> getUsersWithRolesAndPermissions() {
        List<User> users = (List<User>) userRepository.findAll();

        return users.stream().map(user -> {
            // Agrupar por role
            Map<String, List<String>> rolePermissionsMap = user.getUserRolePermissions().stream()
                    .collect(Collectors.groupingBy(
                            urp -> urp.getRolePermission().getRole().getName(),
                            Collectors.mapping(
                                    urp -> urp.getRolePermission().getPermission().getName(),
                                    Collectors.toList())));

            // Convertir a DTOs
            List<RolePermissionsDto> roles = rolePermissionsMap.entrySet().stream()
                    .map(entry -> new RolePermissionsDto(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());

            return new UserWithRolesAndPermissionsDto(user.getId(), user.getUsername(), roles);
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

    @Transactional
    public void assignRolePermissionsToUser(Long userId, List<Long> rolePermissionIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + userId));

        List<RolePermission> rolePermissions = (List<RolePermission>) rolePermissionRepository
                .findAllById(rolePermissionIds);

        List<UserRolePermission> userRolePermissions = rolePermissions.stream()
                .map(rp -> new UserRolePermission(user, rp))
                .collect(Collectors.toList());

        userRolePermissionRepository.saveAll(userRolePermissions);
    }

}
