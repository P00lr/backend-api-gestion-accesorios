package com.universidad.tecno.api_gestion_accesorios.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.Adjustment;

public interface AdjustmentRepository extends CrudRepository<Adjustment, Long>{
    Page<Adjustment> findAll(Pageable Pageable);
}
