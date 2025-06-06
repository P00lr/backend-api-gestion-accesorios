package com.universidad.tecno.api_gestion_accesorios.dto.purchase;

import java.time.LocalDateTime;

public class ListPurchaseDto {
     private Long id;
    private Double totalAmount;
    private Integer totalQuantity;
    private LocalDateTime purchaseDate;
    private String supplierName;
    private String userName;

    
    public ListPurchaseDto() {
    }

    public ListPurchaseDto(Long id, Double totalAmount, Integer totalQuantity, LocalDateTime purchaseDate,
            String supplierName, String userName) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.purchaseDate = purchaseDate;
        this.supplierName = supplierName;
        this.userName = userName;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    
    
    
}
