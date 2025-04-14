package com.universidad.tecno.api_gestion_accesorios.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase_details")
public class PurchaseDetail {

    @EmbeddedId
    private PurchaseDetailId id;

    private Double amountType;
    private Integer quantityType;

    @ManyToOne
    @JsonIgnoreProperties({"purchaseDetails", "handler", "hibernateLazyInitializer"})
    @MapsId("purchaseId")
    private Purchase purchase;

    @ManyToOne
    @JsonIgnoreProperties({"purchaseDetails", "handler", "hibernateLazyInitializer"})
    @MapsId("accessoryId")
    private Accessory accessory;

    

    public PurchaseDetail() {
    }

    
    public PurchaseDetail(PurchaseDetailId id, Double amountType, Integer quantityType, Purchase purchase,
            Accessory accessory) {
        this.id = id;
        this.amountType = amountType;
        this.quantityType = quantityType;
        this.purchase = purchase;
        this.accessory = accessory;
    }


    public PurchaseDetailId getId() {
        return id;
    }

    public void setId(PurchaseDetailId id) {
        this.id = id;
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

    public Accessory getAccessory() {
        return accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    
}
