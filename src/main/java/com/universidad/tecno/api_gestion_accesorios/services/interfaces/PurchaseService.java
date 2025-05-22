package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.universidad.tecno.api_gestion_accesorios.dto.purchase.CreatePurchaseDto;
import com.universidad.tecno.api_gestion_accesorios.dto.purchase.GetPurchaseDto;
import com.universidad.tecno.api_gestion_accesorios.dto.purchase.ListPurchaseDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Purchase;

public interface PurchaseService {
    Page<ListPurchaseDto> paginateAll(Pageable pageable);
    List<Purchase> findAll();
    List<ListPurchaseDto> getAllPurchase();
    Optional<GetPurchaseDto> getPurchaseById(Long id);
    public Purchase createPurchase(CreatePurchaseDto saleDto);
    boolean deleteById(Long id);
}
