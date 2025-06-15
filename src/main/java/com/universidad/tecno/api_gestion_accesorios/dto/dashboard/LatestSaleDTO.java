package com.universidad.tecno.api_gestion_accesorios.dto.dashboard;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LatestSaleDTO {
    private LocalDateTime date;
    private double amount; 
    private Long id;
}
