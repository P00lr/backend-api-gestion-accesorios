package com.universidad.tecno.api_gestion_accesorios.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.universidad.tecno.api_gestion_accesorios.entities.Accessory;

public interface AccessoryRepository extends CrudRepository<Accessory, Long>{
    Page<Accessory> findAll(Pageable pageable);
    @Query("""
    SELECT a FROM Accessory a
    WHERE EXISTS (
        SELECT 1 FROM WarehouseDetail wd
        WHERE wd.accessory.id = a.id
          AND wd.state = 'AVAILABLE'
          AND wd.stock > 0
    )
""")
Page<Accessory> findAllWithAvailableStock(Pageable pageable);


}
