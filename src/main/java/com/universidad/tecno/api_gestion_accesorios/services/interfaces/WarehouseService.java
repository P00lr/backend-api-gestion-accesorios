package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.universidad.tecno.api_gestion_accesorios.entities.Warehouse;

public interface WarehouseService {
    Page<Warehouse> paginateAll(Pageable pageable);

    List<Warehouse> findAll();

    Optional<Warehouse> findById(Long id);

    Warehouse save(Warehouse warehouse);

    Optional<Warehouse> update(Long id, Warehouse warehouse);

    boolean deleteById(Long id);
}
