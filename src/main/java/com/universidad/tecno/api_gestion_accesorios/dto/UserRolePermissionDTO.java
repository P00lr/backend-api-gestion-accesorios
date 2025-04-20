package com.universidad.tecno.api_gestion_accesorios.dto;

public class UserRolePermissionDTO {
    private Long userId;
    private Long rolePermissionId;
    private String roleName;
    private String permissionName;

    public UserRolePermissionDTO(Long userId, Long rolePermissionId, String roleName, String permissionName) {
        this.userId = userId;
        this.rolePermissionId = rolePermissionId;
        this.roleName = roleName;
        this.permissionName = permissionName;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(Long rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
