package com.universidad.tecno.api_gestion_accesorios.entities;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name ="warehouse_details", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"accessory_id", "warehouse_id"})
})
public class WarehouseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String state;
    private Integer stock;
    
    @ManyToOne
    @JoinColumn(name = "accessory_id", nullable = false)
    private Accessory accessory;
    
    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    
    @JsonIgnoreProperties({"warehouseDetail", "sale", "handler", "hibernateLazyInitializer"})
    @OneToMany(mappedBy = "warehouseDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleDetail> saleDetails;
    
    @JsonIgnoreProperties({"warehouseDetail", "purchase", "handler", "hibernateLazyInitializer"})
    @OneToMany(mappedBy = "warehouseDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseDetail> purchaseDetails;
    
    @JsonIgnoreProperties({"warehouseDetail", "transfer", "handler", "hibernateLazyInitializer"})
    @OneToMany(mappedBy = "warehouseDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransferDetail> transferDetails;
    
    @JsonIgnoreProperties({"warehouseDetail", "adjustment", "handler", "hibernateLazyInitializer"})
    @OneToMany(mappedBy = "warehouseDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdjustmentDetail> adjustmentDetails;
    

    public WarehouseDetail() {
    }

    public WarehouseDetail(Long id, String state, Integer stock, Accessory accessory, Warehouse warehouse,
            List<SaleDetail> saleDetails, List<PurchaseDetail> purchaseDetails, List<TransferDetail> transferDetails,
            List<AdjustmentDetail> adjustmentDetails) {
        this.id = id;
        this.state = state;
        this.stock = stock;
        this.accessory = accessory;
        this.warehouse = warehouse;
        this.saleDetails = saleDetails;
        this.purchaseDetails = purchaseDetails;
        this.transferDetails = transferDetails;
        this.adjustmentDetails = adjustmentDetails;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SaleDetail> getSaleDetails() {
        return saleDetails;
    }

    public void setSaleDetails(List<SaleDetail> saleDetails) {
        this.saleDetails = saleDetails;
    }

    public List<PurchaseDetail> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(List<PurchaseDetail> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }

    public List<TransferDetail> getTransferDetails() {
        return transferDetails;
    }

    public void setTransferDetails(List<TransferDetail> transferDetails) {
        this.transferDetails = transferDetails;
    }
    
}
