package com.universidad.tecno.api_gestion_accesorios.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "sale_details")
public class SaleDetail {
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @EmbeddedId
    private SaleDetailId id;

    private Double amountType;
    private Integer quantityType;
    
    @ManyToOne
    @JsonIgnoreProperties({"saleDetails", "handler", "hibernateLazyInitializer"})
    @MapsId("accessoryId")
    private Accessory accessory;
    
    @ManyToOne
    @JsonIgnoreProperties({"saleDetails", "handler", "hibernateLazyInitializer"})
    @MapsId("saleId")
    private Sale sale;

    public SaleDetail() {
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
    public Accessory getAccessory() {
        return accessory;
    }
    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }
    public Sale getSale() {
        return sale;
    }
    public void setSale(Sale sale) {
        this.sale = sale;
    }
    public SaleDetailId getId() {
        return id;
    }
    public void setId(SaleDetailId id) {
        this.id = id;
    }

    
}
