package com.universidad.tecno.api_gestion_accesorios.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopSellingAccessoryDTO {
    private Long id;
    private String accessoryName;
    private long quantitySold;
}
