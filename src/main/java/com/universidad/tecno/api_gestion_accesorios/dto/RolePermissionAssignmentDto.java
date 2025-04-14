package com.universidad.tecno.api_gestion_accesorios.dto;

import java.util.List;

public class RolePermissionAssignmentDto {
    
    private Long roleId;
    private List<Long> permissionIds;

    public RolePermissionAssignmentDto() {
    }

    public RolePermissionAssignmentDto(Long roleId, List<Long> permissionIds) {
        this.roleId = roleId;
        this.permissionIds = permissionIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
