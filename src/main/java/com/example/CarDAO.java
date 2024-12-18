package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/MyCompany";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public List<Car> getAvailableCars() {
        List<Car> cars = new ArrayList<>();
        String carQuery = "SELECT * FROM cars WHERE state = 1";
        String departmentQuery = "SELECT adres FROM departments WHERE depart_id = ?"; 

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement carStmt = conn.prepareStatement(carQuery);
             ResultSet carRs = carStmt.executeQuery()) {

            while (carRs.next()) {
                int departId = carRs.getInt("depart_id");

                
                String departmentAddress = getDepartmentAddress(conn, departId, departmentQuery);

                
                Car car = new Car(
                        carRs.getInt("car_id"),
                        carRs.getString("model"),
                        carRs.getDouble("price"),
                        departId,
                        carRs.getInt("state"),
                        departmentAddress 
                );
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    private String getDepartmentAddress(Connection conn, int departId, String query) {
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, departId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String address = rs.getString("adres");
                if (address != null && !address.isEmpty()) {
                    return address; // Возвращаем адрес департамента
                } else {
                    return "Адрес не найден";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Неизвестен"; // Если адрес не найден или ошибка, возвращаем "Неизвестен"
    }

    public boolean rentCar(int carId, int clientId) {
        String updateQuery = "UPDATE cars SET state = 0 WHERE car_id = ? AND state = 1";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setInt(1, carId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public List<Car> showCarsToAdmin(int adminId) {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT c.*, d.adres AS department_address " +
                       "FROM Cars c " +
                       "JOIN Admins_Departs ad ON c.depart_id = ad.depart_id " +
                       "JOIN Departments d ON c.depart_id = d.depart_id " +
                       "WHERE ad.admin_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int carId = rs.getInt("car_id");
                String model = rs.getString("model");
                double price = rs.getDouble("price");
                int departId = rs.getInt("depart_id");
                int state = rs.getInt("state");
                String departmentAddress = rs.getString("department_address");

                Car car = new Car(carId, model, price, departId, state, departmentAddress);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    public boolean updateCar(int carId, double newPrice, int newState) {
        String query = "UPDATE Cars SET price = ?, state = ? WHERE car_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, newPrice);
            stmt.setInt(2, newState);
            stmt.setInt(3, carId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean addCar(String model, double price, int adminId) {
        int departId = getDepartIdForAdmin(adminId);
        if (departId == -1) {
            return false;
        }

        String query = "INSERT INTO Cars (model, price, depart_id, state) VALUES (?, ?, ?, 1)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, model);
            stmt.setDouble(2, price);
            stmt.setInt(3, departId);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getDepartIdForAdmin(int adminId) {
        String query = "SELECT depart_id FROM Admins_Departs WHERE admin_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("depart_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public boolean deleteCar(int carId) {
        String query = "DELETE FROM Cars WHERE car_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, carId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}

