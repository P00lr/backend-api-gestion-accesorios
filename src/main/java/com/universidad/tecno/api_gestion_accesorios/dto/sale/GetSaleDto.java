package com.universidad.tecno.api_gestion_accesorios.dto.sale;

import java.time.LocalDateTime;
import java.util.List;

public class GetSaleDto {

    private Long id; 
    private Double totalAmount;
    private Integer totalQuantity;
    private LocalDateTime saleDate;
    private String clientName;
    private String clientEmail;
    private String userName;
    private List<GetSaleDetailDto> saleDetails;
    
    public GetSaleDto() {
    }
    public GetSaleDto(Long id, Double totalAmount, Integer totalQuantity, LocalDateTime saleDate, String clientName,
            String clientEmail, String userName, List<GetSaleDetailDto> saleDetails) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.saleDate = saleDate;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.userName = userName;
        this.saleDetails = saleDetails;
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
    public String getClientEmail() {
        return clientEmail;
    }
    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public List<GetSaleDetailDto> getSaleDetails() {
        return saleDetails;
    }
    public void setSaleDetails(List<GetSaleDetailDto> saleDetails) {
        this.saleDetails = saleDetails;
    }

    
}
