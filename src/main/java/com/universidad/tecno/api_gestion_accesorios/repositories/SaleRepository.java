package com.universidad.tecno.api_gestion_accesorios.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.universidad.tecno.api_gestion_accesorios.entities.Sale;

public interface SaleRepository extends CrudRepository<Sale, Long> {
    Page<Sale> findAll(Pageable pageable);

    List<Sale> findBySaleDateBetween(LocalDateTime from, LocalDateTime to);

    // DASHBOARD GLOBAL
    @Query("SELECT SUM(s.totalAmount) FROM Sale s WHERE FUNCTION('MONTH', s.saleDate) = FUNCTION('MONTH', CURRENT_DATE) AND FUNCTION('YEAR', s.saleDate) = FUNCTION('YEAR', CURRENT_DATE)")
    Double getMonthlySalesAmount();

    @Query("SELECT SUM(sd.quantity) FROM SaleDetail sd WHERE FUNCTION('MONTH', sd.sale.saleDate) = FUNCTION('MONTH', CURRENT_DATE) AND FUNCTION('YEAR', sd.sale.saleDate) = FUNCTION('YEAR', CURRENT_DATE)")
    Long getUnitsSoldThisMonth();

    @Query("SELECT COUNT(s) FROM Sale s WHERE DATE(s.saleDate) = CURRENT_DATE")
    Integer countSalesToday();

    @Query("SELECT COUNT(s) FROM Sale s WHERE s.saleDate >= :startOfWeek")
    Integer countSalesThisWeek(@Param("startOfWeek") LocalDateTime startOfWeek);

}

