package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.dto.PermissionDto;
import com.universidad.tecno.api_gestion_accesorios.dto.role.RoleWithPermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Role;
import com.universidad.tecno.api_gestion_accesorios.entities.RolePermission;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.entities.UserRolePermission;
import com.universidad.tecno.api_gestion_accesorios.repositories.RolePermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.RoleRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRolePermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.RoleService;

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
    public Page<Role> paginateAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Page<RoleWithPermissionsDto> paginateAllRoleWithPermissions(Pageable pageable) {
        Page<Role> rolesPage = roleRepository.findAll(pageable);

        List<RoleWithPermissionsDto> dtos = rolesPage.getContent().stream().map(role -> {
            List<PermissionDto> permissions = role.getRolePermissions().stream()
                    .map(rp -> new PermissionDto(
                            rp.getPermission().getId(),
                            rp.getPermission().getName()))
                    .collect(Collectors.toList());

            return new RoleWithPermissionsDto(role.getId(), role.getName(), permissions);
        }).collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, rolesPage.getTotalElements());
    }

    @Override
    public List<RoleWithPermissionsDto> getRolesWithPermissions() {
        List<Role> roles = (List<Role>) roleRepository.findAll();

        return roles.stream().map(role -> {
            List<PermissionDto> permissions = role.getRolePermissions().stream()
                    .map(rp -> new PermissionDto(
                            rp.getPermission().getId(),
                            rp.getPermission().getName()))
                    .collect(Collectors.toList());

            return new RoleWithPermissionsDto(role.getId(), role.getName(), permissions);
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
