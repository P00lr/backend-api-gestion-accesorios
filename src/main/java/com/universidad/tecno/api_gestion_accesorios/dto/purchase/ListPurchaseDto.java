package com.universidad.tecno.api_gestion_accesorios.dto.purchase;

import java.time.LocalDateTime;

public class ListPurchaseDto {
     private Long id;
    private Double totalAmount;
    private Integer totalQuantity;
    private LocalDateTime purchaseDate;
    private Long supplierId;
    private Long userId;

    
    public ListPurchaseDto() {
    }


    public ListPurchaseDto(Long id, Double totalAmount, Integer totalQuantity, LocalDateTime purchaseDate,
            Long supplierId, Long userId) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.purchaseDate = purchaseDate;
        this.supplierId = supplierId;
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


    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }


    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }


    public Long getSupplierId() {
        return supplierId;
    }


    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }


    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    
}
