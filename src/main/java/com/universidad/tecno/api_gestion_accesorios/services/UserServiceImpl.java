package com.universidad.tecno.api_gestion_accesorios.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.dto.role.RolePermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.dto.user.UserWithRolesAndPermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.entities.RolePermission;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.entities.UserRolePermission;
import com.universidad.tecno.api_gestion_accesorios.repositories.RolePermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRolePermissionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private UserRolePermissionRepository userRolePermissionRepository;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<UserWithRolesAndPermissionsDto> getUsersWithRolesAndPermissions() {
        List<User> users = (List<User>) userRepository.findAll();

        return users.stream().map(user -> {
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
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> update(Long id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            if (user.getName() != null)
                existingUser.setName(user.getName());
            if (user.getUsername() != null)
                existingUser.setUsername(user.getUsername());
            if (user.getPassword() != null)
                existingUser.setPassword(user.getPassword());
            if (user.getEmail() != null)
                existingUser.setEmail(user.getEmail());
            return userRepository.save(existingUser);
        });
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<User> userOp = userRepository.findById(id);
        if (userOp.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void assignRolePermissions(Long userId, List<Long> rolePermissionIds) {
        if (rolePermissionIds == null || rolePermissionIds.isEmpty()) {
            throw new IllegalArgumentException("La lista de IDs de permisos no puede estar vacía.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + userId));

        List<RolePermission> rolePermissions = (List<RolePermission>) rolePermissionRepository.findAllById(rolePermissionIds);

        if (rolePermissions.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron RolePermissions con los IDs proporcionados.");
        }

        // Obtener los permisos ya asignados al usuario
        List<UserRolePermission> existentes = userRolePermissionRepository.findByUserId(userId);

        Set<String> existentesKeys = existentes.stream()
                .map(e -> e.getRolePermission().getId().toString())
                .collect(Collectors.toSet());

        // Crear nuevos UserRolePermission sin duplicados
        List<UserRolePermission> nuevos = rolePermissions.stream()
                .filter(rp -> !existentesKeys.contains(rp.getId().toString()))
                .map(rp -> new UserRolePermission(user, rp))
                .collect(Collectors.toList());

        if (!nuevos.isEmpty()) {
            userRolePermissionRepository.saveAll(nuevos);
        }
    }
}
