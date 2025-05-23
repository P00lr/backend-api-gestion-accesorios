package com.universidad.tecno.api_gestion_accesorios.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;

public interface AccessoryRepository extends CrudRepository<Accessory, Long>{
    Page<Accessory> findAll(Pageable pageable);
}
