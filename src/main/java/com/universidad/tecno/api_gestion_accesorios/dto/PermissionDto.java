package com.universidad.tecno.api_gestion_accesorios.dto;

public class PermissionDto {
    private Long id;
    private String name;

    public PermissionDto(Long id, String name) {
        this.id = id;
        this.name = name;
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
    
}
