package com.universidad.tecno.api_gestion_accesorios.dto.adjustment;

import java.time.LocalDateTime;
import java.util.List;

public class GetAdjustmentDto {
    private Long id;
    private LocalDateTime date;
    private String type;
    private String description;
    private String userFullName;
    private List<GetAdjustmentDetailDto> details;

    
    public GetAdjustmentDto() {
    }


    public GetAdjustmentDto(Long id, LocalDateTime date, String type, String description, String userFullName,
            List<GetAdjustmentDetailDto> details) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.description = description;
        this.userFullName = userFullName;
        this.details = details;
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


    public String getUserFullName() {
        return userFullName;
    }


    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }


    public List<GetAdjustmentDetailDto> getDetails() {
        return details;
    }


    public void setDetails(List<GetAdjustmentDetailDto> details) {
        this.details = details;
    }

    
}
