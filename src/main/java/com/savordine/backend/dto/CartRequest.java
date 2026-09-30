package com.savordine.backend.dto;

public class CartRequest {

    private Long userId;
    private Long foodId;
    private Integer quantity;

    // Default Constructor
    public CartRequest() {
    }

    // Constructor
    public CartRequest(Long userId, Long foodId, Integer quantity) {
        this.userId = userId;
        this.foodId = foodId;
        this.quantity = quantity;
    }

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}