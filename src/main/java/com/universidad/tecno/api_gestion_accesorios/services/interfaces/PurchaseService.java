package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.entities.Purchase;

public interface PurchaseService {
    List<Purchase> findAll();
    Optional<Purchase> findById(Long id);
    Purchase save(Purchase purchase);
    Optional<Purchase> update(Long id, Purchase purchase);
    boolean deleteById (Long id);
}
