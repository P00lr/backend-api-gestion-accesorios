package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.universidad.tecno.api_gestion_accesorios.dto.sale.CreateSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.GetSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.ListSaleDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Sale;

public interface SaleService {
    public Page<ListSaleDto> paginateAll(Pageable pageable);
    List<Sale> findAll();
    List<ListSaleDto> getAllSales();
    Optional<GetSaleDto> getSaleById(Long id);
    public Sale createSale(CreateSaleDto saleDto);
    boolean deleteById(Long id);
}
