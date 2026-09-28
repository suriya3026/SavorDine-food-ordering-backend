package com.savordine.backend.dto;

public class OrderRequest {

    private Long userId;
    private String deliveryAddress;

    // Default Constructor
    public OrderRequest() {
    }

    // Constructor
    public OrderRequest(Long userId, String deliveryAddress) {
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
    }

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}