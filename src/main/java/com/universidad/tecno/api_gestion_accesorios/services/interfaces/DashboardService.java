package com.universidad.tecno.api_gestion_accesorios.services.interfaces;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.DashboardDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.LowStockItemDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.TopCustomerDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.TopSellingAccessoryDTO;

public interface DashboardService {
     public DashboardDTO getDashboardData();

     public Page<LowStockItemDTO> getLowStockItems(int threshold, Pageable pageable);

     public Page<TopSellingAccessoryDTO> getTopSellingAccessories(LocalDateTime startDate, LocalDateTime endDate,
               Long categoryId, Pageable pageable);

     public Page<TopCustomerDTO> getTopCustomers(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
