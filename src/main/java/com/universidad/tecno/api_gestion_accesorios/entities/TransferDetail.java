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
@Table(name = "transfer_details", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"transfer_id", "warehouse_detail_id"})
})
public class TransferDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "transfer_id", nullable = false)
    private Transfer transfer;

    @ManyToOne
    @JoinColumn(name = "warehouse_detail_id", nullable =  false)
    private WarehouseDetail warehouseDetail;

    public TransferDetail() {
    }

    public TransferDetail(Long id, Integer quantity, Transfer transfer, WarehouseDetail warehouseDetail) {
        this.id = id;
        this.quantity = quantity;
        this.transfer = transfer;
        this.warehouseDetail = warehouseDetail;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
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
