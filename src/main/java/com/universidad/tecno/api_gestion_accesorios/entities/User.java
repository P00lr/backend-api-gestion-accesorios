package com.universidad.tecno.api_gestion_accesorios.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import static jakarta.persistence.GenerationType.*;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<UserRolePermission> userRolePermissions;

    
    public User() {
        this.enabled = true;
    }
    
    public User(Long id, String name, String username, String password, String email, boolean enabled,
            List<UserRolePermission> userRolePermissions) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.userRolePermissions = userRolePermissions;
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
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public List<UserRolePermission> getUserRolePermissions() {
        return userRolePermissions;
    }
    public void setUserRolePermissions(List<UserRolePermission> userRolePermissions) {
        this.userRolePermissions = userRolePermissions;
    }
     
}
