package com.universidad.tecno.api_gestion_accesorios.dto.adjustment;

public class GetAdjustmentDetailDto {
    private String accessoryName;
    private Integer quantity;
    private String itemDescription;
    public GetAdjustmentDetailDto() {
    }
   
    public GetAdjustmentDetailDto(String accessoryName, Integer quantity, String itemDescription) {
        this.accessoryName = accessoryName;
        this.quantity = quantity;
        this.itemDescription = itemDescription;
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

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    

    
}
