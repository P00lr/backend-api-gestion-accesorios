package com.universidad.tecno.api_gestion_accesorios.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.universidad.tecno.api_gestion_accesorios.entities.WarehouseDetail;

public interface WarehouseDetailRepository extends CrudRepository<WarehouseDetail, Long> {

    // Definir un método de consulta personalizado para encontrar un WarehouseDetail
    // por WarehouseId y AccessoryId
    @Query("SELECT wd FROM WarehouseDetail wd WHERE wd.warehouse.id = :warehouseId AND wd.accessory.id = :accessoryId")
    Optional<WarehouseDetail> findByWarehouseIdAndAccessoryId(Long warehouseId, Long accessoryId);

    Optional<WarehouseDetail> findByAccessoryIdAndWarehouseId(Long accessoryId, Long warehouseId);

    Page<WarehouseDetail> findByStateAndStockGreaterThan(String state, int stock, Pageable pageable);

    List<WarehouseDetail> findByAccessoryIdAndStockGreaterThanAndStateOrderByStockDesc(
            Long accessoryId,
            int stock,
            String state);

    @Query("SELECT SUM(wd.stock) FROM WarehouseDetail wd WHERE wd.accessory.id = :accessoryId AND wd.state = :state")
    Integer sumStockByAccessoryIdAndState(@Param("accessoryId") Long accessoryId, @Param("state") String state);


}
