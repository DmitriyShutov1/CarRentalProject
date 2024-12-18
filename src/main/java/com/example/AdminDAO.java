package com.example;

import java.sql.*;

public class AdminDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/MyCompany";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found!");
            e.printStackTrace();
        }
    }
    
    public Integer authenticateAdmin(String login, int passwordHash) {
        String query = "SELECT admin_id FROM Admins WHERE login = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.setInt(2, passwordHash);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("admin_id"); // Возвращаем ID администратора
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Если администратор не найден, возвращаем null
    }

    // Метод для получения администратора по логину
    public Admin getAdminByLogin(String login) {
        String query = "SELECT * FROM Admins WHERE login = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Admin(
                    rs.getInt("admin_id"),
                    rs.getString("login"),
                    rs.getInt("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Если администратор не найден, возвращаем null
    }
}
