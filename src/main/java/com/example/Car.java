package com.example;

public class Car {
    private int carId;
    private String model;
    private double price;
    private int departId;
    private int state;
    private String departmentAddress; 


    public Car(int carId, String model, double price, int departId, int state, String departmentAddress) {
        this.carId = carId;
        this.model = model;
        this.price = price;
        this.departId = departId;
        this.state = state;
        this.departmentAddress = departmentAddress;
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

    public int getDepartId() {
        return departId;
    }

    public void setDepartId(int departId) {
        this.departId = departId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDepartmentAddress() {
        return departmentAddress;
    }

    public void setDepartmentAddress(String departmentAddress) {
        this.departmentAddress = departmentAddress;
    }
}
