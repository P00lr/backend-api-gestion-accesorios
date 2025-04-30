package com.universidad.tecno.api_gestion_accesorios.dto.transfer;

import java.util.List;

public class CreateTransferDto {
    private String description;
    private Long userId;
    private Long originWarehouseId;
    private Long destinationWarehouseId;
    private List<CreateTransferDetailDto> transferDetails;

    public CreateTransferDto() {
    }
    
    public CreateTransferDto(String description, Long userId, Long originWarehouseId, Long destinationWarehouseId,
            List<CreateTransferDetailDto> transferDetails) {
        this.description = description;
        this.userId = userId;
        this.originWarehouseId = originWarehouseId;
        this.destinationWarehouseId = destinationWarehouseId;
        this.transferDetails = transferDetails;
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
    public Long getOriginWarehouseId() {
        return originWarehouseId;
    }
    public void setOriginWarehouseId(Long originWarehouseId) {
        this.originWarehouseId = originWarehouseId;
    }
    public Long getDestinationWarehouseId() {
        return destinationWarehouseId;
    }
    public void setDestinationWarehouseId(Long destinationWarehouseId) {
        this.destinationWarehouseId = destinationWarehouseId;
    }
    public List<CreateTransferDetailDto> getTransferDetails() {
        return transferDetails;
    }
    public void setTransferDetails(List<CreateTransferDetailDto> transferDetails) {
        this.transferDetails = transferDetails;
    }

    
}
