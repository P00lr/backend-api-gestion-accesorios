package com.universidad.tecno.api_gestion_accesorios.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name ="warehouseDetails")
public class WarehouseDetail {

    @EmbeddedId
    private WarehouseDetailId id;

    private String state;
    private Integer stock;

    @ManyToOne
    @JsonIgnoreProperties({"warehouseDetails", "handler", "hibernateLazyInitializer"})
    @MapsId("warehouseId")
    private Warehouse warehouse;

    @ManyToOne
    @JsonIgnoreProperties({"warehouseDetails", "handler", "hibernateLazyInitializer"})
    @MapsId("accessoryId")
    private Accessory accessory;

    
    public WarehouseDetail() {
    }

    public WarehouseDetail(WarehouseDetailId id, String state, Integer stock, Warehouse warehouse,
            Accessory accessory) {
        this.id = id;
        this.state = state;
        this.stock = stock;
        this.warehouse = warehouse;
        this.accessory = accessory;
    }

    public WarehouseDetailId getId() {
        return id;
    }

    public void setId(WarehouseDetailId id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    


}
