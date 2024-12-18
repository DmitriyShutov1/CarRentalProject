package com.example;

public class Order {
    private int orderId;
    private int carId;
    private String model;
    private double price;

    public Order(int orderId, int carId, String model, double price) {
        this.orderId = orderId;
        this.carId = carId;
        this.model = model;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
