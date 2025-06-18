package com.universidad.tecno.api_gestion_accesorios.dto.dashboard;


import lombok.Data;

@Data
public class DashboardDTO {
    private TotalsDTO totals;
    private SalesSummaryDTO salesSummary;

    public DashboardDTO() {
    }

    public DashboardDTO(TotalsDTO totals, SalesSummaryDTO salesSummary) {
        this.totals = totals;
        this.salesSummary = salesSummary;
    }
}
