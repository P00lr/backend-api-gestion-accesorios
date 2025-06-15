package com.universidad.tecno.api_gestion_accesorios.dto.dashboard;


import lombok.Data;

@Data
public class LatestUserDTO {
    private Long id;
    private String name;
    private String email;
    
    public LatestUserDTO() {
    }

    public LatestUserDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
