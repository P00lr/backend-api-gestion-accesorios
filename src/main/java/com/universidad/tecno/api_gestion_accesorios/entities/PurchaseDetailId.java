package com.universidad.tecno.api_gestion_accesorios.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class PurchaseDetailId implements Serializable{

    private Long purchaseId;
    private Long accessoryId;
    
    public PurchaseDetailId() {
    }

    public PurchaseDetailId(Long purchaseId, Long accessoryId) {
        this.purchaseId = purchaseId;
        this.accessoryId = accessoryId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getAccessory() {
        return accessoryId;
    }

    public void setAccessory(Long accessoryId) {
        this.accessoryId = accessoryId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((purchaseId == null) ? 0 : purchaseId.hashCode());
        result = prime * result + ((accessoryId == null) ? 0 : accessoryId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PurchaseDetailId other = (PurchaseDetailId) obj;
        if (purchaseId == null) {
            if (other.purchaseId != null)
                return false;
        } else if (!purchaseId.equals(other.purchaseId))
            return false;
        if (accessoryId == null) {
            if (other.accessoryId != null)
                return false;
        } else if (!accessoryId.equals(other.accessoryId))
            return false;
        return true;
    }

    

    

}
