package com.universidad.tecno.api_gestion_accesorios.dto.user;

import java.util.List;

import com.universidad.tecno.api_gestion_accesorios.dto.PermissionDto;

public class UserWithRolesAndPermissionsDto {
    private Long userId;
    private String name;
    private String userName;
    private List<PermissionDto> permissions;

   

    public UserWithRolesAndPermissionsDto(Long userId, String name, String userName, List<PermissionDto> permissions) {
        this.userId = userId;
        this.name = name;
        this.userName = userName;
        this.permissions = permissions;
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
