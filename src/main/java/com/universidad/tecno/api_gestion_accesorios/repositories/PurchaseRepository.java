package com.universidad.tecno.api_gestion_accesorios.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Long>{
    Page<Purchase> findAll(Pageable pageable);
}
