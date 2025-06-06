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

        // ---------REPORTE INVENTORY------------
        @Query("SELECT wd FROM WarehouseDetail wd " +
                        "JOIN FETCH wd.accessory a " +
                        "JOIN FETCH wd.warehouse w " +
                        "JOIN FETCH a.category c " +
                        "WHERE (:warehouseIds IS NULL OR w.id IN :warehouseIds) " +
                        "AND (:accessoryIds IS NULL OR a.id IN :accessoryIds) " +
                        "AND (:categoryIds IS NULL OR c.id IN :categoryIds)")
        List<WarehouseDetail> findWithFilters(
                        @Param("warehouseIds") List<Long> warehouseIds,
                        @Param("accessoryIds") List<Long> accessoryIds,
                        @Param("categoryIds") List<Long> categoryIds);

}
