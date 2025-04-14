package com.universidad.tecno.api_gestion_accesorios.entities;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class WarehouseDetailId implements Serializable{

    private Long warehouseId;
    private Long accessoryId;

    public WarehouseDetailId(Long warehouseId, Long accessoryId) {
        this.warehouseId = warehouseId;
        this.accessoryId = accessoryId;
    }
    public WarehouseDetailId() {
    }

    public Long getWarehouseId() {
        return warehouseId;
    }
    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
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
        result = prime * result + ((warehouseId == null) ? 0 : warehouseId.hashCode());
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
        WarehouseDetailId other = (WarehouseDetailId) obj;
        if (warehouseId == null) {
            if (other.warehouseId != null)
                return false;
        } else if (!warehouseId.equals(other.warehouseId))
            return false;
        if (accessoryId == null) {
            if (other.accessoryId != null)
                return false;
        } else if (!accessoryId.equals(other.accessoryId))
            return false;
        return true;
    }
    
}
