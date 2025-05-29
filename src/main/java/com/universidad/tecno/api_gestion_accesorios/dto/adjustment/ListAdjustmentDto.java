package com.universidad.tecno.api_gestion_accesorios.dto.adjustment;

import java.time.LocalDateTime;

public class ListAdjustmentDto {
     private Long id;
    private LocalDateTime date;
    private String type;
    private String description;
    private Integer totalQuantity;
    private String warehouseName;

    public ListAdjustmentDto() {
    }

    public ListAdjustmentDto(Long id, LocalDateTime date, String type, String description, Integer totalQuantity) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.description = description;
        this.totalQuantity = totalQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    
    
}
