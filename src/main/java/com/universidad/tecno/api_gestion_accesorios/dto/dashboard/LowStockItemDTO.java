package com.universidad.tecno.api_gestion_accesorios.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LowStockItemDTO {
    private Long accessoryId;
    private String name;
    private int stock; 
}
