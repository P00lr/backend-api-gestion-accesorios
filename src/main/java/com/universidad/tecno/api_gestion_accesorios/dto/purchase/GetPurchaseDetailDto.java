package com.universidad.tecno.api_gestion_accesorios.dto.purchase;

public class GetPurchaseDetailDto {
    private Long accessoryId;
    private String accessoryName;
    private Double price;
    private Integer quantityType;
    private Double amountType;
    private String warehouseName;

    
    public GetPurchaseDetailDto() {
    }


    public GetPurchaseDetailDto(Long accessoryId, String accessoryName, Double price, Integer quantityType,
            Double amountType, String warehouseName) {
        this.accessoryId = accessoryId;
        this.accessoryName = accessoryName;
        this.price = price;
        this.quantityType = quantityType;
        this.amountType = amountType;
        this.warehouseName = warehouseName;
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


    public Double getPrice() {
        return price;
    }


    public void setPrice(Double price) {
        this.price = price;
    }


    public Integer getQuantityType() {
        return quantityType;
    }


    public void setQuantityType(Integer quantityType) {
        this.quantityType = quantityType;
    }


    public Double getAmountType() {
        return amountType;
    }


    public void setAmountType(Double amountType) {
        this.amountType = amountType;
    }


    public String getWarehouseName() {
        return warehouseName;
    }


    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    
    
}
