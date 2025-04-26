package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.entities.Supplier;

public interface SupplierService {

    List<Supplier> findAll();
    Optional<Supplier> findById(Long id);
    Supplier save(Supplier supplier);
    Optional<Supplier> update(Long id, Supplier supplier);
    boolean deleteById(Long id);
}
