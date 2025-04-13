package com.universidad.tecno.api_gestion_accesorios.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_roles_permissions")
public class UserRolePermission {

    @EmbeddedId
    private UserRolePermissionId id;

    @ManyToOne
    @MapsId("userId")
    @JsonIgnoreProperties({"userRolePermissions", "handler", "hibernateLazyInitializer"})
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JsonIgnoreProperties({"userRolePermissions", "handler", "hibernateLazyInitializer"})
    private Role role;

    @ManyToOne
    @MapsId("permissionId")
    @JsonIgnoreProperties({"userRolePermissions", "handler", "hibernateLazyInitializer"})
    private Permission permission;

    

    public UserRolePermission() {
    }

    public UserRolePermission(UserRolePermissionId id, User user, Role role, Permission permission) {
        this.id = id;
        this.user = user;
        this.role = role;
        this.permission = permission;
    }


    public UserRolePermissionId getId() {
        return id;
    }

    public void setId(UserRolePermissionId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    

}
