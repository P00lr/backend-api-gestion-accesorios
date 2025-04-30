package com.universidad.tecno.api_gestion_accesorios.dto.sale;

public class GetSaleDetailDto {

    private Long accessoryId;  // Id del accesorio
    private String accessoryName;  // Nombre del accesorio
    private Double price;
    private Integer quantityType;  // Cantidad del accesorio en la venta
    private Double amountType;
    
    public GetSaleDetailDto() {
    }

    public GetSaleDetailDto(Long accessoryId, String accessoryName, Double price, Integer quantityType,
            Double amountType) {
        this.accessoryId = accessoryId;
        this.accessoryName = accessoryName;
        this.price = price;
        this.quantityType = quantityType;
        this.amountType = amountType;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    } 

    
}
