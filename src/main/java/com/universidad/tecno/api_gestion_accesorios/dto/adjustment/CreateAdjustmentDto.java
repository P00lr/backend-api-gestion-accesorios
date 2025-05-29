package com.universidad.tecno.api_gestion_accesorios.dto.adjustment;

import java.time.LocalDateTime;
import java.util.List;

public class CreateAdjustmentDto {
    private String type;
    private LocalDateTime date;
    private String description;
    private Long userId;
    private Long WarehouseId;
    private List<CreateAdjustmentDetailDto> adjustmentDetails;
    
    public CreateAdjustmentDto() {
    }
    
    public CreateAdjustmentDto(LocalDateTime date, String type, String description, Long userId, Long warehouseId,
            List<CreateAdjustmentDetailDto> adjustmentDetails) {
        this.date = date;
        this.type = type;
        this.description = description;
        this.userId = userId;
        WarehouseId = warehouseId;
        this.adjustmentDetails = adjustmentDetails;
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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public List<CreateAdjustmentDetailDto> getAdjustmentDetails() {
        return adjustmentDetails;
    }
    public void setAdjustmentDetails(List<CreateAdjustmentDetailDto> adjustmentDetails) {
        this.adjustmentDetails = adjustmentDetails;
    }

    public Long getWarehouseId() {
        return WarehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        WarehouseId = warehouseId;
    }

}
