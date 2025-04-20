package com.universidad.tecno.api_gestion_accesorios.dto.user;

import java.util.List;

import com.universidad.tecno.api_gestion_accesorios.dto.role.RolePermissionsDto;

public class UserWithRolesAndPermissionsDto {
    private Long userId;
    private String userName;
    private List<RolePermissionsDto> roles;

    public UserWithRolesAndPermissionsDto(Long userId, String userName, List<RolePermissionsDto> roles) {
        this.userId = userId;
        this.userName = userName;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<RolePermissionsDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RolePermissionsDto> roles) {
        this.roles = roles;
    }
    
}
