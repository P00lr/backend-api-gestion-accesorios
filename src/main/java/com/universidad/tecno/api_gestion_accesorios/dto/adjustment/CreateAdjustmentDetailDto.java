package com.universidad.tecno.api_gestion_accesorios.dto.adjustment;

public class CreateAdjustmentDetailDto {
    private Long warehouseDetailId;
    private Integer quantity;
    public CreateAdjustmentDetailDto() {
    }
    public CreateAdjustmentDetailDto(Long warehouseDetailId, Integer quantity) {
        this.warehouseDetailId = warehouseDetailId;
        this.quantity = quantity;
    }
    public Long getWarehouseDetailId() {
        return warehouseDetailId;
    }
    public void setWarehouseDetailId(Long warehouseDetailId) {
        this.warehouseDetailId = warehouseDetailId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    

    
}
