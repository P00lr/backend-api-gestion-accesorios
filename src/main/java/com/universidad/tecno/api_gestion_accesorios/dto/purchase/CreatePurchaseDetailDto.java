package com.universidad.tecno.api_gestion_accesorios.dto.purchase;

public class CreatePurchaseDetailDto {
    private Long accessoryId;
    private Integer quantityType;

    public CreatePurchaseDetailDto() {
    }

    public CreatePurchaseDetailDto(Long accessoryId, Integer quantityType) {
        this.accessoryId = accessoryId;
        this.quantityType = quantityType;
    }

    public Integer getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(Integer quantityType) {
        this.quantityType = quantityType;
    }

    public Long getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(Long accessoryId) {
        this.accessoryId = accessoryId;
    }
    
    
}
