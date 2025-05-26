package com.universidad.tecno.api_gestion_accesorios.dto.accessory;

public class GetAccessories {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean available;
    private Integer stock;
    private Long categoryId;
    private String categoryName;

    public GetAccessories() {
    }

    public GetAccessories(Long id, String name, String description, Double price, Boolean available, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.stock = stock;
    }
    

    public GetAccessories(Long id, String name, String description, Double price, Boolean available, Integer stock,
            Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.stock = stock;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
