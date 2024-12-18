package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/MyCompany";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    // Метод для сохранения отзыва
    public boolean saveReview(int orderId, String reviewText) {
        String insertQuery = "INSERT INTO reviews (order_id, text) VALUES (?, ?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setInt(1, orderId);  // Устанавливаем идентификатор заказа
            stmt.setString(2, reviewText); // Устанавливаем текст отзыва

            int rowsInserted = stmt.executeUpdate();  // Выполняем вставку данных
            return rowsInserted > 0;  // Возвращаем true, если вставка успешна
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Возвращаем false в случае ошибки
    }

    // Метод для получения отзыва по идентификатору заказа
    public String getReviewByOrderId(int orderId) {
        String selectQuery = "SELECT text FROM reviews WHERE order_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {

            stmt.setInt(1, orderId);  // Устанавливаем идентификатор заказа
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("text");  // Возвращаем текст отзыва
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Возвращаем null, если отзыва нет
    }
    
    
    
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT r.order_id, r.text, o.client_id, c.model " +
                       "FROM reviews r " +
                       "JOIN orders o ON r.order_id = o.order_id " +
                       "JOIN cars c ON o.car_id = c.car_id";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int clientId = rs.getInt("client_id");
                String carModel = rs.getString("model");
                String reviewText = rs.getString("text");
                reviews.add(new Review(clientId, carModel, reviewText));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
    
    
    
    public boolean deleteReviewsByOrderId(int orderId) {
        String query = "DELETE FROM Reviews WHERE order_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Reviews for order with ID " + orderId + " successfully deleted.");
                return true;
            } else {
                System.out.println("No reviews found for order with ID " + orderId + ".");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting reviews for order with ID " + orderId + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
