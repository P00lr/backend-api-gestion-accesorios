package com.universidad.tecno.api_gestion_accesorios.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.tecno.api_gestion_accesorios.dto.role.RolePermissionAssignmentDto;
import com.universidad.tecno.api_gestion_accesorios.dto.user.AssignRolePermissionsToUserRequest;
import com.universidad.tecno.api_gestion_accesorios.entities.Permission;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.PermissionService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Permission> permissions = permissionService.paginateAll(pageable);
        return ResponseEntity.ok(permissions);
    }

    @GetMapping
    public List<Permission> getPermissions() {
        return permissionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermission(@PathVariable Long id) {
        return permissionService.findById(id)
                .map(permission -> ResponseEntity.ok(permission))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        Permission createdPermission = permissionService.save(permission);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPermission);
    }

    // asignamos permisos a los roles
    @PostMapping("/assign-to-role")
    public ResponseEntity<Void> assignPermissionsToRole(
            @RequestBody RolePermissionAssignmentDto dto) {

        permissionService.assignPermissionsToRole(dto.getRoleId(), dto.getPermissionIds());
        return ResponseEntity.ok().build();
    }

    // assign permission to user

    @PostMapping("/assign-to-user")
    public ResponseEntity<?> assignPermissionsToUser(@RequestBody AssignRolePermissionsToUserRequest request) {
        permissionService.assignPermissionsToUser(request.getUserId(), request.getPermissionIds());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody Permission permission) {
        return permissionService.update(id, permission)
                .map(updatePermission -> ResponseEntity.ok(updatePermission))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        boolean deleted = permissionService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
