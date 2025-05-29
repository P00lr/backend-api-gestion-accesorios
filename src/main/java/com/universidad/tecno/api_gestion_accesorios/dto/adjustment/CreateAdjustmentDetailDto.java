package com.universidad.tecno.api_gestion_accesorios.dto.adjustment;

public class CreateAdjustmentDetailDto {
    private Long accessoryId;
    private String itemDescription;
    private Integer quantity;
    public CreateAdjustmentDetailDto() {
    }
    
    

    public CreateAdjustmentDetailDto(Long accessoryId, String itemDescription, Integer quantity) {
        this.accessoryId = accessoryId;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
    }
    

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }



    public Long getAccessoryId() {
        return accessoryId;
    }



    public void setAccessoryId(Long accessoryId) {
        this.accessoryId = accessoryId;
    }



    public String getItemDescription() {
        return itemDescription;
    }



    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    
    

    
}
