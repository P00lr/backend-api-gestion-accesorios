package com.universidad.tecno.api_gestion_accesorios.services.interfaces;


import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.dto.transfer.CreateTransferDto;
import com.universidad.tecno.api_gestion_accesorios.dto.transfer.GetTransferDto;
import com.universidad.tecno.api_gestion_accesorios.dto.transfer.ListTransferDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Transfer;

public interface TransferService {
    public List<ListTransferDto> findAll();
    public Optional<GetTransferDto> findById(Long id);
    public Transfer createTransfer(CreateTransferDto dto);
    public boolean deleteTransfer(Long id);
}
