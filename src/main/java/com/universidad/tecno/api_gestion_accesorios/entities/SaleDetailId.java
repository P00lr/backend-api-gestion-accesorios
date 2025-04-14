package com.universidad.tecno.api_gestion_accesorios.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class SaleDetailId implements Serializable{
    
    private Long saleId;
    private Long accessoryId;

    public SaleDetailId() {
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Long getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(Long accessoryId) {
        this.accessoryId = accessoryId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((saleId == null) ? 0 : saleId.hashCode());
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
        SaleDetailId other = (SaleDetailId) obj;
        if (saleId == null) {
            if (other.saleId != null)
                return false;
        } else if (!saleId.equals(other.saleId))
            return false;
        if (accessoryId == null) {
            if (other.accessoryId != null)
                return false;
        } else if (!accessoryId.equals(other.accessoryId))
            return false;
        return true;
    }

   

    
}   
