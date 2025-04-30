package com.universidad.tecno.api_gestion_accesorios.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "purchase_details", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"purchase_id", "warehouse_detail_id"})
})
public class PurchaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amountType;
    private Integer quantityType;

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "warehouse_detail_id", nullable = false)    
    private WarehouseDetail warehouseDetail;

    public PurchaseDetail() {
    }
    
    public PurchaseDetail(Long id, Double amountType, Integer quantityType, Purchase purchase,
            WarehouseDetail warehouseDetail) {
        this.id = id;
        this.amountType = amountType;
        this.quantityType = quantityType;
        this.purchase = purchase;
        this.warehouseDetail = warehouseDetail;
    }

    public Double getAmountType() {
        return amountType;
    }

    public void setAmountType(Double amountType) {
        this.amountType = amountType;
    }

    public Integer getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(Integer quantityType) {
        this.quantityType = quantityType;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public WarehouseDetail getWarehouseDetail() {
        return warehouseDetail;
    }

    public void setWarehouseDetail(WarehouseDetail warehouseDetail) {
        this.warehouseDetail = warehouseDetail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    
}
