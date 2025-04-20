package com.universidad.tecno.api_gestion_accesorios.dto.role;

import java.util.List;

public class RolePermissionsDto {
    private String roleName;
    private List<String> permissions;

    public RolePermissionsDto(String roleName, List<String> permissions) {
        this.roleName = roleName;
        this.permissions = permissions;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
    
}
