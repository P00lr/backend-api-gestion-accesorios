package com.universidad.tecno.api_gestion_accesorios.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.tecno.api_gestion_accesorios.dto.AssignRolePermissionsToUserRequest;
import com.universidad.tecno.api_gestion_accesorios.dto.RoleWithPermissionsDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Role;
import com.universidad.tecno.api_gestion_accesorios.services.RoleService;
import com.universidad.tecno.api_gestion_accesorios.services.UserService;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @GetMapping("/with-permissions")
    public ResponseEntity<List<RoleWithPermissionsDto>> listRolesWithPermissionsDto() {
        List<RoleWithPermissionsDto> roles = roleService.getRolesWithPermissions();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRol(@PathVariable Long id) {
        return roleService.findById(id)
                .map(role -> ResponseEntity.ok(role))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Role> createdRole(@RequestBody Role role) {
        Role createdRole = roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    // asigna roles a los users
    @PostMapping("/assign-role-permissions")
    public ResponseEntity<String> assignRolePermissionsToUser(@RequestBody AssignRolePermissionsToUserRequest request) {
        userService.assignRolePermissions(request.getUserId(), request.getRolePermissionIds());
        return ResponseEntity.ok("Permisos asignados correctamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
        return roleService.update(id, role)
                .map(updateRole -> ResponseEntity.ok(updateRole))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        boolean deleted = roleService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
