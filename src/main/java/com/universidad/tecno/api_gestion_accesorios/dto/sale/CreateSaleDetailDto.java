package com.universidad.tecno.api_gestion_accesorios.dto.sale;

public class CreateSaleDetailDto {

    private Long accessoryId;
    private Integer quantity;

    public CreateSaleDetailDto() {
    }
    
    public CreateSaleDetailDto(Long accessoryId, Integer quantity) {
        this.accessoryId = accessoryId;
        this.quantity = quantity;
    }

    public Long getAccessoryId() {
        return accessoryId;
    }
    public void setAccessoryId(Long accessoryId) {
        this.accessoryId = accessoryId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    

    
}
