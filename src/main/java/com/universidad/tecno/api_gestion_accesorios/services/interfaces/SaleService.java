package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itextpdf.text.DocumentException;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.CreateSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.GetSaleDto;
import com.universidad.tecno.api_gestion_accesorios.dto.sale.ListSaleDto;
import com.universidad.tecno.api_gestion_accesorios.entities.Sale;

public interface SaleService {
    public Page<ListSaleDto> paginateAll(Pageable pageable);
    List<Sale> findAll();
    List<ListSaleDto> getAllSales();
    Optional<GetSaleDto> getSaleById(Long id);
    public void processSale(CreateSaleDto dto);
    boolean deleteById(Long id);

    //----------REPORTES------------
    public List<Sale> getSalesBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
     public byte[] generateSalesReportBetweenDates(LocalDateTime startDate, LocalDateTime endDate)
            throws DocumentException;
}
