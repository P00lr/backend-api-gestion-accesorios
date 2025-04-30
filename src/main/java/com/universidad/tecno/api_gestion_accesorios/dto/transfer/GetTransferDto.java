package com.universidad.tecno.api_gestion_accesorios.dto.transfer;

import java.time.LocalDateTime;
import java.util.List;

public class GetTransferDto {
    private Long id;
    private String description;
    private LocalDateTime date;
    private String originWarehouse;
    private String destinationWarehouse;
    private String userName;
    private List<GetTransferDetailDto> details;

    public GetTransferDto(Long id, String description, LocalDateTime date, String originWarehouse,
                          String destinationWarehouse, String userName, List<GetTransferDetailDto> details) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.originWarehouse = originWarehouse;
        this.destinationWarehouse = destinationWarehouse;
        this.userName = userName;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getOriginWarehouse() {
        return originWarehouse;
    }

    public void setOriginWarehouse(String originWarehouse) {
        this.originWarehouse = originWarehouse;
    }

    public String getDestinationWarehouse() {
        return destinationWarehouse;
    }

    public void setDestinationWarehouse(String destinationWarehouse) {
        this.destinationWarehouse = destinationWarehouse;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<GetTransferDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<GetTransferDetailDto> details) {
        this.details = details;
    }

    
}
