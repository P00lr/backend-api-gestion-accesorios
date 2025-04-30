package com.universidad.tecno.api_gestion_accesorios.dto.sale;

import java.time.LocalDateTime;

public class ListSaleDto {
    private Long id;
    private Double totalAmount;
    private Integer totalQuantity;
    private LocalDateTime saleDate;
    private Long clientId;
    private Long userId;
    
    public ListSaleDto() {
    }
    public ListSaleDto(Long id, Double totalAmount, Integer totalQuantity, LocalDateTime saleDate, Long clientId,
            Long userId) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.saleDate = saleDate;
        this.clientId = clientId;
        this.userId = userId;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Integer getTotalQuantity() {
        return totalQuantity;
    }
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    public LocalDateTime getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
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

    
}
