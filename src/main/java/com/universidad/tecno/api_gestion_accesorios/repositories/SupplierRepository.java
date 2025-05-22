package com.universidad.tecno.api_gestion_accesorios.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.Supplier;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {
    Page<Supplier> findAll(Pageable pageable);
}
