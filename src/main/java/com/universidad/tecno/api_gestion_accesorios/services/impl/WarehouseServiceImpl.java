package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.entities.Warehouse;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.WarehouseService;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public List<Warehouse> findAll() {
        return (List<Warehouse>) warehouseRepository.findAll();
    }

    @Override
    public Optional<Warehouse> findById(Long id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Override
    public Optional<Warehouse> update(Long id, Warehouse warehouse) {
        return warehouseRepository.findById(id).map(existingWarehouse -> {
            if(warehouse.getName() != null)
                existingWarehouse.setName(warehouse.getName());
            if(warehouse.getAddress() != null)
                existingWarehouse.setAddress(warehouse.getAddress());
            return warehouseRepository.save(existingWarehouse);
        });
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Warehouse> warehouseOp = warehouseRepository.findById(id);
        if(warehouseOp.isPresent()){
            warehouseRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
