package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.universidad.tecno.api_gestion_accesorios.dto.sale.CreateSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.GetSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.ListSaleDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Sale;

public interface SaleService {
    List<Sale> findAll();
    List<ListSaleDto> getAllSales();
    Optional<GetSaleDto> getSaleById(Long id);
    public Sale createSale(CreateSaleDto saleDto);
    boolean deleteById(Long id);
}
