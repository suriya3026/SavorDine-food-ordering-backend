package com.savordine.backend.dto;

public class FoodRequest {

    private String name;
    private String description;
    private Double price;
    private String image;
    private boolean available;
    private Long categoryId;

    // Default Constructor
    public FoodRequest() {
    }

    // Constructor
    public FoodRequest(String name,
                       String description,
                       Double price,
                       String image,
                       boolean available,
                       Long categoryId) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.available = available;
        this.categoryId = categoryId;
    }

    // Getters and Setters

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}