package com.universidad.tecno.api_gestion_accesorios.dto.warehouse;

public class GetWarehouseDetail {
    private Long id;
    private Long accessoryId;
    private String accessoryName;
    private Long warehouseId;
    private String warehouseName;
    private Integer stock;
    private String state;

    public GetWarehouseDetail() {
    }

    

    public GetWarehouseDetail(Long id, Long accessoryId, String accessoryName, Long warehouseId, String warehouseName,
            Integer stock, String state) {
        this.id = id;
        this.accessoryId = accessoryId;
        this.accessoryName = accessoryName;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.stock = stock;
        this.state = state;
    }



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Long getWarehouseId() {
        return warehouseId;
    }
    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
    public String getWarehouseName() {
        return warehouseName;
    }
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    
    
}
