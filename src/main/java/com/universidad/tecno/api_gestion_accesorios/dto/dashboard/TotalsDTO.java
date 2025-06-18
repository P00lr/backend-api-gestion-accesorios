package com.universidad.tecno.api_gestion_accesorios.dto.dashboard;

import lombok.Data;

@Data
public class TotalsDTO {
    private double monthlySales;
    private Long unitsSold;
    private double monthlyPurchases;
    private Long unitsPurchased;
    private Long accessories;
    private Long users;
    
    public TotalsDTO() {
    }

    public TotalsDTO(double monthlySales, Long unitsSold, double monthlyPurchases, Long unitsPurchased,
            Long accessories, Long users) {
        this.monthlySales = monthlySales;
        this.unitsSold = unitsSold;
        this.monthlyPurchases = monthlyPurchases;
        this.unitsPurchased = unitsPurchased;
        this.accessories = accessories;
        this.users = users;
    }
}
