package com.universidad.tecno.api_gestion_accesorios.dto.transfer;

public class CreateTransferDetailDto {
    private Long warehouseDetailId; // ID del detalle del almacén de origen
    private Integer quantity;
    public CreateTransferDetailDto() {
    }
    public CreateTransferDetailDto(Long warehouseDetailId, Integer quantity) {
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
