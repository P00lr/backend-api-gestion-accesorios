package com.universidad.tecno.api_gestion_accesorios.dto.adjustment;

public class GetAdjustmentDetailDto {
    private Long warehouseDetailId;
    private String accessoryName;
    private String warehouseName;
    private Integer quantity;
    public GetAdjustmentDetailDto() {
    }
    public GetAdjustmentDetailDto(Long warehouseDetailId, String accessoryName, String warehouseName,
            Integer quantity) {
        this.warehouseDetailId = warehouseDetailId;
        this.accessoryName = accessoryName;
        this.warehouseName = warehouseName;
        this.quantity = quantity;
    }
    public Long getWarehouseDetailId() {
        return warehouseDetailId;
    }
    public void setWarehouseDetailId(Long warehouseDetailId) {
        this.warehouseDetailId = warehouseDetailId;
    }
    public String getAccessoryName() {
        return accessoryName;
    }
    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }
    public String getWarehouseName() {
        return warehouseName;
    }
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    
}
