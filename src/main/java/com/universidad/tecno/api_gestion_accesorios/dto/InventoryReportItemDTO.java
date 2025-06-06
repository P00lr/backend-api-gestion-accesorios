package com.universidad.tecno.api_gestion_accesorios.dto;

import java.math.BigDecimal;

public class InventoryReportItemDTO {
    private String warehouseName;
    private String accessoryName;
    private String categoryName;
    private BigDecimal unitPrice;
    private int stock;
    private BigDecimal totalValue;

    
    public InventoryReportItemDTO(String warehouseName, String accessoryName, String categoryName, BigDecimal unitPrice,
            int stock, BigDecimal totalValue) {
        this.warehouseName = warehouseName;
        this.accessoryName = accessoryName;
        this.categoryName = categoryName;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.totalValue = totalValue;
    }

    
    public InventoryReportItemDTO() {
    }
    public String getWarehouseName() {
        return warehouseName;
    }
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    public String getAccessoryName() {
        return accessoryName;
    }
    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public BigDecimal getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    
}
