package com.universidad.tecno.api_gestion_accesorios.dto.purchase;

import java.time.LocalDateTime;
import java.util.List;

public class GetPurchaseDto {
    private Long id;
    private Double totalAmount;
    private Integer totalQuantity;
    private LocalDateTime purchaseDate;
    private String supplierName;
    private String supplierEmail;
    private String userName;
    private List<GetPurchaseDetailDto> purchaseDetails;
    public GetPurchaseDto() {
    }
    public GetPurchaseDto(Long id, Double totalAmount, Integer totalQuantity, LocalDateTime purchaseDate,
            String supplierName, String supplierEmail, String userName, List<GetPurchaseDetailDto> purchaseDetails) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.purchaseDate = purchaseDate;
        this.supplierName = supplierName;
        this.supplierEmail = supplierEmail;
        this.userName = userName;
        this.purchaseDetails = purchaseDetails;
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
    public String getSupplierEmail() {
        return supplierEmail;
    }
    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public List<GetPurchaseDetailDto> getPurchaseDetails() {
        return purchaseDetails;
    }
    public void setPurchaseDetails(List<GetPurchaseDetailDto> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }

    
}
