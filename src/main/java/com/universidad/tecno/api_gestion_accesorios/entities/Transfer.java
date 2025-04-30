package com.universidad.tecno.api_gestion_accesorios.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String description;


    @ManyToOne
    private User user;

    @ManyToOne
    private Warehouse originWarehouse ;

    @ManyToOne
    private Warehouse destinationWarehouse;

    @JsonIgnoreProperties({"transfer", "warehouseDetail", "handler", "hibernateLazyInitializer"})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "transfer")
    private List<TransferDetail> transferDetails;

    public Transfer() {
    }

    public Transfer(Long id, LocalDateTime date, String description, User user, Warehouse originWarehouse,
            Warehouse destinationWarehouse, List<TransferDetail> transferDetails) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.user = user;
        this.originWarehouse = originWarehouse;
        this.destinationWarehouse = destinationWarehouse;
        this.transferDetails = transferDetails;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Warehouse getOriginWarehouse() {
        return originWarehouse;
    }

    public void setOriginWarehouse(Warehouse originWarehouse) {
        this.originWarehouse = originWarehouse;
    }

    public Warehouse getDestinationWarehouse() {
        return destinationWarehouse;
    }

    public void setDestinationWarehouse(Warehouse destinationWarehouse) {
        this.destinationWarehouse = destinationWarehouse;
    }

    public List<TransferDetail> getTransferDetails() {
        return transferDetails;
    }

    public void setTransferDetails(List<TransferDetail> transferDetails) {
        this.transferDetails = transferDetails;
    }
    

    
}
