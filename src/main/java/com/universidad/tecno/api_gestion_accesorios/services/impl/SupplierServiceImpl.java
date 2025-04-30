package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.entities.Supplier;
import com.universidad.tecno.api_gestion_accesorios.repositories.SupplierRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.SupplierService;


@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> findAll() {
        return (List<Supplier>) supplierRepository.findAll();
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Optional<Supplier> update(Long id, Supplier supplier) {
        
        return supplierRepository.findById(id).map( updateSupplier -> {
            if(supplier.getName() != null)
                updateSupplier.setName(supplier.getName());
            if(supplier.getAddress() != null)
            updateSupplier.setAddress(supplier.getAddress());
            if (supplier.getEmail() != null)
            updateSupplier.setEmail(supplier.getEmail());
            return supplierRepository.save(updateSupplier); 
        });


    }

    @Override
    public boolean deleteById(Long id) {
        
        Optional<Supplier> existingSupplier = supplierRepository.findById(id);

        if(existingSupplier.isPresent()) {
            supplierRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
