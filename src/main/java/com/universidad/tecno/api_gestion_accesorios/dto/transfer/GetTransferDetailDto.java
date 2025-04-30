package com.universidad.tecno.api_gestion_accesorios.dto.transfer;

public class GetTransferDetailDto {
    private Long accessoryId;
    private String accessoryName;
    private Integer quantity;
    private Integer currentStock;

    public GetTransferDetailDto(Long accessoryId, String accessoryName, Integer quantity, Integer currentStock) {
        this.accessoryId = accessoryId;
        this.accessoryName = accessoryName;
        this.quantity = quantity;
        this.currentStock = currentStock;
    }

    public Long getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(Long accessoryId) {
        this.accessoryId = accessoryId;
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }
    
}
