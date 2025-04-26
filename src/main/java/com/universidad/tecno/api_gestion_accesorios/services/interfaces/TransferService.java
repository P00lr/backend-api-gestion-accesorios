package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.entities.Transfer;

public interface TransferService {
    List<Transfer> findAll();
    Optional<Transfer> findById(Long id);
    Transfer save(Transfer transfer);
    Optional<Transfer> update(Long id, Transfer transfer);
    boolean deleteById(Long id);
}
