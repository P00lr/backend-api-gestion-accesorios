package com.universidad.tecno.api_gestion_accesorios.repositories;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.TopCustomerDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.TopSellingAccessoryDTO;
import com.universidad.tecno.api_gestion_accesorios.entities.SaleDetail;

public interface SaleDetailRepository extends CrudRepository<SaleDetail, Long> {

       // dashboard
       //top
       @Query("""
            SELECT new com.universidad.tecno.api_gestion_accesorios.dto.dashboard.TopSellingAccessoryDTO(
                w.accessory.id,
                w.accessory.name,
                SUM(sd.quantity)
            )
            FROM SaleDetail sd
            JOIN sd.warehouseDetail w
            JOIN sd.sale s
            WHERE s.saleDate BETWEEN :startDate AND :endDate
            GROUP BY w.accessory.id, w.accessory.name
            ORDER BY SUM(sd.quantity) DESC
        """)
        Page<TopSellingAccessoryDTO> findTopSellingAccessoriesByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
        );

      @Query("""
          SELECT new com.universidad.tecno.api_gestion_accesorios.dto.dashboard.TopSellingAccessoryDTO(
              w.accessory.id,
              w.accessory.name,
              SUM(sd.quantity)
          )
          FROM SaleDetail sd
          JOIN sd.warehouseDetail w
          JOIN sd.sale s
          WHERE s.saleDate BETWEEN :startDate AND :endDate
            AND w.accessory.category.id = :categoryId
          GROUP BY w.accessory.id, w.accessory.name
          ORDER BY SUM(sd.quantity) DESC
      """)
      Page<TopSellingAccessoryDTO> findTopSellingAccessoriesByCategoryAndDateRange(
          @Param("categoryId") Long categoryId,
          @Param("startDate") LocalDateTime startDate,
          @Param("endDate") LocalDateTime endDate,
          Pageable pageable
      );


       @Query("""
          SELECT new com.universidad.tecno.api_gestion_accesorios.dto.dashboard.TopCustomerDTO(
              s.user.id,
              s.user.name,
              s.user.email,
              SUM(s.totalAmount),
              COUNT(s)
          )
          FROM Sale s
          WHERE s.saleDate BETWEEN :startDate AND :endDate
          GROUP BY s.user.id, s.user.name, s.user.email
          ORDER BY SUM(s.totalAmount) DESC
      """)
      Page<TopCustomerDTO> findTopCustomersByDateRange(
          @Param("startDate") LocalDateTime startDate,
          @Param("endDate") LocalDateTime endDate,
          Pageable pageable
      );

}
