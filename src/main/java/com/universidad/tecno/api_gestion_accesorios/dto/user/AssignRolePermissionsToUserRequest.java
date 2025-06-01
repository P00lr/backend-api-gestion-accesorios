package com.universidad.tecno.api_gestion_accesorios.dto.user;

import java.util.List;

public class AssignRolePermissionsToUserRequest {
    private Long userId;
    private List<Long> permissionIds;
    
    public AssignRolePermissionsToUserRequest() {
    }
    
    public AssignRolePermissionsToUserRequest(Long userId, List<Long> permissionIds) {
        this.userId = userId;
        this.permissionIds = permissionIds;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
    
    
}
