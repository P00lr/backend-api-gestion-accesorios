package com.universidad.tecno.api_gestion_accesorios.dto.role;

import java.util.List;

import com.universidad.tecno.api_gestion_accesorios.dto.user.PermissionsDto;


public class RolePermissionsDto {
    private Long id;
    private String roleName;
    private List<PermissionsDto> permissions;

    public RolePermissionsDto(Long id, String roleName, List<PermissionsDto> permissions) {
        this.id = id;
        this.roleName = roleName;
        this.permissions = permissions;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PermissionsDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionsDto> permissions) {
        this.permissions = permissions;
    }
    
}
