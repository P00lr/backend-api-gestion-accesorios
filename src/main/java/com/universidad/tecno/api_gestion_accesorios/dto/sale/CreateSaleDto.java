package com.universidad.tecno.api_gestion_accesorios.dto.sale;

import java.util.List;

public class CreateSaleDto {
    
    private Long clientId;
    private Long userId;
    private List<CreateSaleDetailDto> saleDetails;
    
    public CreateSaleDto() {
    }
    public CreateSaleDto(Long clientId, Long userId, List<CreateSaleDetailDto> saleDetails) {
        this.clientId = clientId;
        this.userId = userId;
        this.saleDetails = saleDetails;
    }
    
    public Long getClientId() {
        return clientId;
    }
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public List<CreateSaleDetailDto> getSaleDetails() {
        return saleDetails;
    }
    public void setSaleDetails(List<CreateSaleDetailDto> saleDetails) {
        this.saleDetails = saleDetails;
    }

    
}
