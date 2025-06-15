package com.universidad.tecno.api_gestion_accesorios.dto.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class TopCustomerDTO {
    private Long id;
    private String name;
    private String email;
    private Double totalAmount;
    private long salesCount;

    public TopCustomerDTO(Long id, String name, String email, Double totalAmount, long salesCount) {
        this.id = id;
        this.name = name;
        this.email = email;
        // Redondear a 2 decimales
        this.totalAmount = totalAmount != null
                ? BigDecimal.valueOf(totalAmount).setScale(2, RoundingMode.HALF_UP).doubleValue()
                : 0.0;
        this.salesCount = salesCount;
    }
}

