package com.universidad.tecno.api_gestion_accesorios.dto;

import java.util.List;

public class AssignRolePermissionsToUserRequest {
    private Long userId;
    private List<Long> rolePermissionIds;
    
    public AssignRolePermissionsToUserRequest() {
    }
    public AssignRolePermissionsToUserRequest(Long userId, List<Long> rolePermissionIds) {
        this.userId = userId;
        this.rolePermissionIds = rolePermissionIds;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public List<Long> getRolePermissionIds() {
        return rolePermissionIds;
    }
    public void setRolePermissionIds(List<Long> rolePermissionIds) {
        this.rolePermissionIds = rolePermissionIds;
    }
    
}
