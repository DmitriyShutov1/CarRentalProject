package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/MyCompany";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
	private static final boolean True = false;
    
    private ReviewDAO reviewDAO;

    public OrderDAO() {
        this.reviewDAO = new ReviewDAO();
    }

    
    public boolean createOrder(int clientId, int carId) {
        String insertQuery = "INSERT INTO orders (car_id, client_id, state) VALUES (?, ?, ?)";
        String updateClientQuery = "UPDATE clients SET orders_count = orders_count + 1 WHERE client_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
             PreparedStatement updateClientStmt = conn.prepareStatement(updateClientQuery)) {

            // Создаем заказ
            insertStmt.setInt(1, carId);
            insertStmt.setInt(2, clientId);
            insertStmt.setInt(3, 1); // Заказ в статусе "не завершен"
            int rowsAffected = insertStmt.executeUpdate();

            if (rowsAffected > 0) {
                updateClientStmt.setInt(1, clientId);
                int rowsUpdated = updateClientStmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Order> getClientsOrders(int clientId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT o.order_id, o.car_id, c.model, c.price " +
                       "FROM orders o " +
                       "JOIN cars c ON o.car_id = c.car_id " +
                       "WHERE o.client_id = ? AND o.state = 1"; 

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getInt("car_id"),
                        rs.getString("model"),
                        rs.getDouble("price")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public boolean completeOrder(int orderId) {
        String updateOrderQuery = "UPDATE orders SET state = 0 WHERE order_id = ? AND state != 0";
        String updateClientQuery = "UPDATE clients SET orders_count = orders_count - 1 WHERE client_id = (SELECT client_id FROM orders WHERE order_id = ?)";
        String selectCarQuery = "SELECT car_id FROM orders WHERE order_id = ?";
        String updateCarStateQuery = "UPDATE cars SET state = 1 WHERE car_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement updateOrderStmt = conn.prepareStatement(updateOrderQuery);
             PreparedStatement updateClientStmt = conn.prepareStatement(updateClientQuery);
             PreparedStatement selectCarStmt = conn.prepareStatement(selectCarQuery);
             PreparedStatement updateCarStateStmt = conn.prepareStatement(updateCarStateQuery)) {

            // Шаг 1: Завершаем заказ
            updateOrderStmt.setInt(1, orderId);
            int rowsUpdated = updateOrderStmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Шаг 2: Обновляем счётчик заказов клиента
                updateClientStmt.setInt(1, orderId);
                updateClientStmt.executeUpdate();

                // Шаг 3: Получаем car_id из заказа
                selectCarStmt.setInt(1, orderId);
                ResultSet rs = selectCarStmt.executeQuery();

                if (rs.next()) {
                    int carId = rs.getInt("car_id");

                    // Шаг 4: Обновляем состояние автомобиля
                    updateCarStateStmt.setInt(1, carId);
                    int rowsCarUpdated = updateCarStateStmt.executeUpdate();

                    return rowsCarUpdated > 0; // Возвращаем успех, если автомобиль обновлён
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Возвращаем false в случае ошибки
    }
    
    
    
    
    public boolean deleteOrdersByClientId(int clientId) {
        String selectQuery = "SELECT order_id FROM Orders WHERE client_id = ?";
        String deleteQuery = "DELETE FROM Orders WHERE client_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

            selectStmt.setInt(1, clientId);
            ResultSet rs = selectStmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                reviewDAO.deleteReviewsByOrderId(orderId);
            }

            deleteStmt.setInt(1, clientId);
            int rowsDeleted = deleteStmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Orders for client with ID " + clientId + " successfully deleted.");
                return true;
            } else {
                System.out.println("No orders found for client with ID " + clientId + ".");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting orders for client with ID " + clientId + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
