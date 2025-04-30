package com.universidad.tecno.api_gestion_accesorios.dto.transfer;

import java.time.LocalDateTime;

public class ListTransferDto {
    
    private Long id;
    private String description;
    private LocalDateTime date;
    private String userName;
    public ListTransferDto() {
    }
    public ListTransferDto(Long id, String description, LocalDateTime date, String userName) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.userName = userName;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
}
