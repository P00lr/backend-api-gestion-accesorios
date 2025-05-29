package com.universidad.tecno.api_gestion_accesorios.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.AdjustmentDetail;

public interface AdjustmentDetailRepository extends CrudRepository<AdjustmentDetail, Long> {
    List<AdjustmentDetail> findByWarehouseDetailAccessoryId(Long accessoryId);
}
