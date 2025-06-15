package com.universidad.tecno.api_gestion_accesorios.dto.dashboard;

import lombok.Data;

@Data
public class SalesSummaryDTO {
    private int today;
    private int week;

    public SalesSummaryDTO() {
    }

    public SalesSummaryDTO(int today, int week) {
        this.today = today;
        this.week = week;
    }
}
