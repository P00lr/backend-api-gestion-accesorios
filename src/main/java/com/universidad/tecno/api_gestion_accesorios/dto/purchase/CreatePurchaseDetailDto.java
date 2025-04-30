package com.universidad.tecno.api_gestion_accesorios.dto.purchase;

public class CreatePurchaseDetailDto {
    private Long warehouseDetailId;
    private Integer quantityType;

    public CreatePurchaseDetailDto() {
    }

    public CreatePurchaseDetailDto(Long warehouseDetailId, Integer quantityType) {
        this.warehouseDetailId = warehouseDetailId;
        this.quantityType = quantityType;
    }

    public Long getWarehouseDetailId() {
        return warehouseDetailId;
    }

    public void setWarehouseDetailId(Long warehouseDetailId) {
        this.warehouseDetailId = warehouseDetailId;
    }

    public Integer getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(Integer quantityType) {
        this.quantityType = quantityType;
    }
    
    
}
