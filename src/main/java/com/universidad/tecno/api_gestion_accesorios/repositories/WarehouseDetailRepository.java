package com.universidad.tecno.api_gestion_accesorios.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.WarehouseDetail;

public interface WarehouseDetailRepository extends CrudRepository<WarehouseDetail, Long>{
    
    //Definir un método de consulta personalizado para encontrar un WarehouseDetail por WarehouseId y AccessoryId
    @Query("SELECT wd FROM WarehouseDetail wd WHERE wd.warehouse.id = :warehouseId AND wd.accessory.id = :accessoryId")
    Optional<WarehouseDetail> findByWarehouseIdAndAccessoryId(Long warehouseId, Long accessoryId);
}
