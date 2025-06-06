package com.universidad.tecno.api_gestion_accesorios.dto.sale;

import java.time.LocalDateTime;

public class ListSaleDto {
    private Long id;
    private Double totalAmount;
    private Integer totalQuantity;
    private LocalDateTime saleDate;
    private String clientName;
    
    public ListSaleDto() {
    }
    
    public ListSaleDto(Long id, Double totalAmount, Integer totalQuantity, LocalDateTime saleDate, String clientName) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.saleDate = saleDate;
        this.clientName = clientName;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
}
