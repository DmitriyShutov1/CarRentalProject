package com.example;

public class Client {
    private int clientId;
    private String login;
    private int passwordHash;
    private String bankCard;
    private String phoneNumber;
    private int ordersCount;

    // Конструктор по умолчанию
    public Client() {}

    // Конструктор со всеми параметрами
    public Client(int clientId, String login, int passwordHash, String bankCard, String phoneNumber, int ordersCount) {
        this.clientId = clientId;
        this.login = login;
        this.passwordHash = passwordHash;
        this.bankCard = bankCard;
        this.phoneNumber = phoneNumber;
        this.ordersCount = ordersCount;
    }

    // Геттеры и сеттеры
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }
}
