package com.example;

public class Review {
    private int clientId;
    private String carModel;
    private String reviewText;

    public Review(int clientId, String carModel, String reviewText) {
        this.clientId = clientId;
        this.carModel = carModel;
        this.reviewText = reviewText;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}