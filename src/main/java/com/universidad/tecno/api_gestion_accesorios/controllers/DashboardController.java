package com.universidad.tecno.api_gestion_accesorios.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.DashboardDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.LowStockItemDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.TopCustomerDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.TopSellingAccessoryDTO;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.DashboardService;

@RestController
@RequestMapping("api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboardData() {
        DashboardDTO dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<Page<LowStockItemDTO>> getLowStockItems(
            @RequestParam(defaultValue = "5") int threshold,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("stock").ascending());
        Page<LowStockItemDTO> result = dashboardService.getLowStockItems(threshold, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/top-selling-accessories")
    public ResponseEntity<Page<TopSellingAccessoryDTO>> getTopSellingAccessories(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) Long categoryId // ya está opcional
    ) {
        if (startDate == null)
            startDate = LocalDateTime.MIN;
        if (endDate == null)
            endDate = LocalDateTime.now();

        Pageable pageable = PageRequest.of(page, size);
        Page<TopSellingAccessoryDTO> result = dashboardService.getTopSellingAccessories(startDate, endDate, categoryId,
                pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/top-customers")
    public ResponseEntity<Page<TopCustomerDTO>> getTopCustomers(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        if (startDate == null)
            startDate = LocalDateTime.MIN;
        if (endDate == null)
            endDate = LocalDateTime.now();

        Pageable pageable = PageRequest.of(page, size);
        Page<TopCustomerDTO> result = dashboardService.getTopCustomers(startDate, endDate, pageable);
        return ResponseEntity.ok(result);
    }

}
