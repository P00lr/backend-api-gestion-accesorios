package com.universidad.tecno.api_gestion_accesorios.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "views")
public class ViewCounter {
     
    @Id
    private String page;
    private Long views;

    
    public ViewCounter(String page, Long views) {
        this.page = page;
        this.views = views;
    }
    public ViewCounter() {
    }
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public Long getViews() {
        return views;
    }
    public void setViews(Long views) {
        this.views = views;
    }

    
}
