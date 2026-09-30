package com.savordine.backend.dto;

public class CategoryRequest {

    private String name;
    private String description;
    private String image;

    // Default Constructor
    public CategoryRequest() {
    }

    // Constructor
    public CategoryRequest(String name,
                           String description,
                           String image) {

        this.name = name;
        this.description = description;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}