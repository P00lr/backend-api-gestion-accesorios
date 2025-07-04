package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.dto.PermissionDto;
import com.universidad.tecno.api_gestion_accesorios.dto.user.UserWithRolesAndPermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.entities.RolePermission;
import com.universidad.tecno.api_gestion_accesorios.entities.User;
import com.universidad.tecno.api_gestion_accesorios.entities.UserRolePermission;
import com.universidad.tecno.api_gestion_accesorios.repositories.RolePermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRolePermissionRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private UserRolePermissionRepository userRolePermissionRepository;

    @Override
    public Page<User> paginateAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    //lista a los users para que sea consumido por assign to user
    @Override
    public UserWithRolesAndPermissionsDto getUserWithRolesAndPermissions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        // Obtener permisos únicos asignados al usuario a través de UserRolePermissions
        List<PermissionDto> permissions = user.getUserRolePermissions().stream()
                .map(urp -> urp.getRolePermission().getPermission())
                .distinct() // evitar permisos repetidos
                .map(permission -> new PermissionDto(permission.getId(), permission.getName()))
                .collect(Collectors.toList());

        return new UserWithRolesAndPermissionsDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                permissions);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> update(Long id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            if (user.getName() != null)
                existingUser.setName(user.getName());
            if (user.getUsername() != null)
                existingUser.setUsername(user.getUsername());
            if (user.getPassword() != null && !user.getPassword().isEmpty())
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getEmail() != null)
                existingUser.setEmail(user.getEmail());
            if (user.isEnabled() != null)
                existingUser.setEnabled(user.isEnabled());

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

        List<RolePermission> rolePermissions = (List<RolePermission>) rolePermissionRepository
                .findAllById(rolePermissionIds);

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
