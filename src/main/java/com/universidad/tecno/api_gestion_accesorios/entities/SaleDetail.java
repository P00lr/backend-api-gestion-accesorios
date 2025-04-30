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
@Table(name = "sale_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "sale_id", "warehouse_detail_id" })
})
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amountType;
    private Integer quantityType;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "warehouse_detail_id", nullable = false)
    private WarehouseDetail warehouseDetail;

    public SaleDetail() {
    }

    public SaleDetail(Long id, Double amountType, Integer quantityType, Sale sale, WarehouseDetail warehouseDetail) {
        this.id = id;
        this.amountType = amountType;
        this.quantityType = quantityType;
        this.sale = sale;
        this.warehouseDetail = warehouseDetail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public WarehouseDetail getWarehouseDetail() {
        return warehouseDetail;
    }

    public void setWarehouseDetail(WarehouseDetail warehouseDetail) {
        this.warehouseDetail = warehouseDetail;
    }

}
