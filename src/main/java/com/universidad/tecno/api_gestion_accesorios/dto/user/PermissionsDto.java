package com.universidad.tecno.api_gestion_accesorios.dto.user;

public class PermissionsDto {
    private Long id;
    private String name;
    private Boolean assigned;
    public PermissionsDto() {
    }
    public PermissionsDto(Long id, String name, Boolean assigned) {
        this.id = id;
        this.name = name;
        this.assigned = assigned;
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
    public Boolean getAssigned() {
        return assigned;
    }
    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    
}
