package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.universidad.tecno.api_gestion_accesorios.dto.user.UserWithRolesAndPermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.entities.User;

public interface UserService {
    Page<User> paginateAll(Pageable pageable);
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    Optional<User> update(Long id, User user);
    boolean deleteById(Long id);
    
    //asignamos rolesPermisos a los users
    void assignRolePermissions(Long userId, List<Long> rolePermissionIds);

    List<UserWithRolesAndPermissionsDto> getUsersWithRolesAndPermissions();

}
