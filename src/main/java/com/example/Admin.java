package com.example;

public class Admin {
    private int adminId;
    private String login;
    private int passwordHash;

    // Конструктор по умолчанию
    public Admin() {}

    // Конструктор со всеми параметрами
    public Admin(int adminId, String login, int passwordHash) {
        this.adminId = adminId;
        this.login = login;
        this.passwordHash = passwordHash;
    }

    // Геттеры и сеттеры
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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
}

