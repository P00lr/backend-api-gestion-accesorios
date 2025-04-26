package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.entities.Adjustment;

public interface AdjustmentService {
    List<Adjustment> findAll();
    Optional<Adjustment> findById(Long id);
    Optional<Adjustment> update(Long id, Adjustment adjustment);
    Adjustment save(Adjustment adjustment);
    boolean deleteById(Long id); 
}
