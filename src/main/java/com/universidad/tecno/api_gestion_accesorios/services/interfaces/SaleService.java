package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.entities.Sale;

public interface SaleService {
    List<Sale> findAll();
    Optional<Sale> findById (Long id);
    Sale save(Sale sale);
    Optional<Sale> update(Long id, Sale sale);
    boolean deleteById(Long id);
}
