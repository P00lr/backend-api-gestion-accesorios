package com.universidad.tecno.api_gestion_accesorios.dto.user;

import java.util.List;

public class UserWithPermissionsDto {
    private Long id;
    private String name;
    private String username;
    private List<String> permissions;

    public UserWithPermissionsDto() {
    }
    
    public UserWithPermissionsDto(Long id, String name, String username, List<String> permissions) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public List<String> getPermissions() {
        return permissions;
    }
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
