package com.universidad.tecno.api_gestion_accesorios.dto;

import java.util.List;

public class UserRolePermissionsResponseDto {
    private Long userId;
    private String userName;
    private String roleName;
    private List<String> permissions;
    public UserRolePermissionsResponseDto() {
    }
    public UserRolePermissionsResponseDto(Long userId, String userName, String roleName, List<String> permissions) {
        this.userId = userId;
        this.userName = userName;
        this.roleName = roleName;
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
