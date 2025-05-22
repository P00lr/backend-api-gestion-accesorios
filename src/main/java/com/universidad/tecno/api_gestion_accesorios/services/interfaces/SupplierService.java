package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.universidad.tecno.api_gestion_accesorios.entities.Supplier;

public interface SupplierService {

    Page<Supplier> paginateAll(Pageable pageable);
    List<Supplier> findAll();
    Optional<Supplier> findById(Long id);
    Supplier save(Supplier supplier);
    Optional<Supplier> update(Long id, Supplier supplier);
    boolean deleteById(Long id);
}
