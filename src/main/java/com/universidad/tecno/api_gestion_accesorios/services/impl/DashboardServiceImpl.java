package com.universidad.tecno.api_gestion_accesorios.services.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.DashboardDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.LowStockItemDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.SalesSummaryDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.TopCustomerDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.TopSellingAccessoryDTO;
import com.universidad.tecno.api_gestion_accesorios.dto.dashboard.TotalsDTO;
import com.universidad.tecno.api_gestion_accesorios.repositories.AccessoryRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.PurchaseRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.SaleDetailRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.SaleRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.UserRepository;
import com.universidad.tecno.api_gestion_accesorios.repositories.WarehouseDetailRepository;
import com.universidad.tecno.api_gestion_accesorios.services.interfaces.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private WarehouseDetailRepository warehouseDetailRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaleDetailRepository saleDetailRepository;

    public DashboardDTO getDashboardData() {
        DashboardDTO dto = new DashboardDTO();

        // Totales
        TotalsDTO totals = new TotalsDTO();

        // Ventas del mes
        double monthlySales = saleRepository.getMonthlySalesAmount();
        double roundedMonthlySales = Math.round(monthlySales * 100.0) / 100.0;
        totals.setMonthlySales(roundedMonthlySales);

        // Unidades vendidas del mes
        Long unitsSold = saleRepository.getUnitsSoldThisMonth();
        totals.setUnitsSold(unitsSold != null ? unitsSold : 0L);

        // Compras del mes
        double monthlyPurchases = purchaseRepository.getMonthlyPurchasesAmount();
        double roundedMonthlyPurchases = Math.round(monthlyPurchases * 100.0) / 100.0;
        totals.setMonthlyPurchases(roundedMonthlyPurchases);

        // Unidades compradas del mes
        Long unitsPurchased = purchaseRepository.getUnitsPurchasedThisMonth();
        totals.setUnitsPurchased(unitsPurchased != null ? unitsPurchased : 0L);

        totals.setAccessories(accessoryRepository.getTotalAccessoriesCount());
        totals.setUsers(userRepository.getTotalUsersCount());
        dto.setTotals(totals);

        // Resumen de ventas
        SalesSummaryDTO summary = new SalesSummaryDTO();
        summary.setToday(saleRepository.countSalesToday());

        LocalDateTime startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay();
        summary.setWeek(saleRepository.countSalesThisWeek(startOfWeek));
        dto.setSalesSummary(summary);

        return dto;
    }

    // stock bajo
    @Override
    public Page<LowStockItemDTO> getLowStockItems(int threshold, Pageable pageable) {
        return warehouseDetailRepository.findLowStockItems(threshold, pageable);
    }

    // top accesorio mas vendido
    @Override
    public Page<TopSellingAccessoryDTO> getTopSellingAccessories(LocalDateTime startDate, LocalDateTime endDate,
            Long categoryId, Pageable pageable) {
        if (categoryId != null) {
            return saleDetailRepository.findTopSellingAccessoriesByCategoryAndDateRange(categoryId, startDate, endDate,
                    pageable);
        } else {
            return saleDetailRepository.findTopSellingAccessoriesByDateRange(startDate, endDate, pageable);
        }
    }

    // top usuario que mas compra
    @Override
    public Page<TopCustomerDTO> getTopCustomers(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return saleDetailRepository.findTopCustomersByDateRange(startDate, endDate, pageable);
    }

}
