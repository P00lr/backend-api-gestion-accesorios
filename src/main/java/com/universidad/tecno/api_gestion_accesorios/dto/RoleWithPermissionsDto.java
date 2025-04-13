package com.universidad.tecno.api_gestion_accesorios.dto;

import java.util.List;

public class RoleWithPermissionsDto {
    private Long id;
    private String name;
    private List<PermissionDto> permissions;

    public RoleWithPermissionsDto(Long id, String name, List<PermissionDto> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDto> permissions) {
        this.permissions = permissions;
    }
    
}
