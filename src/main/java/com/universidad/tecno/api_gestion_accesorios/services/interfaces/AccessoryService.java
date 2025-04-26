package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;

public interface AccessoryService {
    List<Accessory> findAll();
    Optional<Accessory> findById(Long id);
    Accessory save(Accessory accessory);
    Optional<Accessory> update(Long id, Accessory accessory);
    boolean deleteById(Long id);
}
