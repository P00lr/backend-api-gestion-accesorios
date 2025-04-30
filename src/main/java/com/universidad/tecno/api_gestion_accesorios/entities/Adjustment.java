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
@Table(name = "adjustments")
public class Adjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String type;
    private String description;

    @JsonIgnoreProperties({"adjustment", "warehouseDetail", "handler", "hibernateLazyInitializer"})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "adjustment")
    private List<AdjustmentDetail> adjustmentDetails;

    @ManyToOne
    private User user;

    public Adjustment() {
    }
    
    public Adjustment(Long id, LocalDateTime date, String type, String description,
            List<AdjustmentDetail> adjustmentDetails, User user) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.description = description;
        this.adjustmentDetails = adjustmentDetails;
        this.user = user;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<AdjustmentDetail> getAdjustmentDetails() {
        return adjustmentDetails;
    }

    public void setAdjustmentDetails(List<AdjustmentDetail> adjustmentDetails) {
        this.adjustmentDetails = adjustmentDetails;
    }
    
    
}
