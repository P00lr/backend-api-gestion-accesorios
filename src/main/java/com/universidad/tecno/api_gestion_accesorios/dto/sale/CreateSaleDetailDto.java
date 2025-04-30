package com.universidad.tecno.api_gestion_accesorios.dto.sale;

public class CreateSaleDetailDto {

    private Long accessoryId;
    private Integer quantityType;

    public CreateSaleDetailDto() {
    }
    public CreateSaleDetailDto(Long accessoryId, Integer quantityType) {
        this.accessoryId = accessoryId;
        this.quantityType = quantityType;
    }
    public Long getAccessoryId() {
        return accessoryId;
    }
    public void setAccessoryId(Long accessoryId) {
        this.accessoryId = accessoryId;
    }
    public Integer getQuantityType() {
        return quantityType;
    }
    public void setQuantityType(Integer quantityType) {
        this.quantityType = quantityType;
    }

    
}
