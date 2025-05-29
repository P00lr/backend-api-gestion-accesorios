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
@Table(name = "adjustment_details", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"adjustment_id", "warehouse_detail_id"})
})
public class AdjustmentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private String itemDescription;

    @ManyToOne
    @JoinColumn(name = "adjustment_id", nullable = false)
    private Adjustment adjustment;

    @ManyToOne
    @JoinColumn(name = "warehouse_detail_id", nullable = false)
    private WarehouseDetail warehouseDetail;

    
    public AdjustmentDetail() {
    }

    public AdjustmentDetail(Long id, Integer quantity, String itemDescription, Adjustment adjustment,
            WarehouseDetail warehouseDetail) {
        this.id = id;
        this.quantity = quantity;
        this.itemDescription = itemDescription;
        this.adjustment = adjustment;
        this.warehouseDetail = warehouseDetail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Adjustment getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Adjustment adjustment) {
        this.adjustment = adjustment;
    }

    public WarehouseDetail getWarehouseDetail() {
        return warehouseDetail;
    }

    public void setWarehouseDetail(WarehouseDetail warehouseDetail) {
        this.warehouseDetail = warehouseDetail;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }


}
