package com.example;

import java.sql.*;

public class ClientDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/MyCompany";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    private OrderDAO orderDAO;

    public ClientDAO() {
        this.orderDAO = new OrderDAO();
        createTriggers();
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found!");
            e.printStackTrace();
        }
    }
    
    public Integer authenticateClient(String login, int passwordHash) {
        String query = "SELECT client_id FROM Clients WHERE login = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.setInt(2, passwordHash);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("client_id"); // Возвращаем ID клиента
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Возвращаем null, если пользователь не найден
    }

    // Регистрация клиента
    public boolean registerClient(String login, int passwordHash, String phone, String card) {
        // Проверяем, существует ли уже клиент с таким логином
        if (isLoginTaken(login)) {
            return false; // Если логин уже занят, регистрация не удалась
        }

        String query = "INSERT INTO Clients (login, password, bank_card, phone_number) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            stmt.setInt(2, passwordHash);
            stmt.setString(3, card);
            stmt.setString(4, phone);
            stmt.executeUpdate();
            return true; // Успешная регистрация
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Метод для проверки, занят ли логин
    private boolean isLoginTaken(String login) {
        String query = "SELECT * FROM Clients WHERE login = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Если результат есть, значит логин уже занят
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public boolean deleteClient(int clientId) {
        System.out.println("Start deleting DAO");

        
		// Сначала удаляем все заказы для данного клиента
        boolean ordersDeleted = orderDAO .deleteOrdersByClientId(clientId);
        if (!ordersDeleted) {
            System.out.println("No orders found for client with ID " + clientId + ". Proceeding with client deletion.");
        }

        String query = "DELETE FROM Clients WHERE client_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            System.out.println("Connection established");
            stmt.setInt(1, clientId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Client with ID " + clientId + " successfully deleted.");
                return true;
            } else {
                System.out.println("No client found with ID " + clientId + ".");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting client with ID " + clientId + ": " + e.getMessage());
            System.out.println("error");
            e.printStackTrace();
            return false;
        }
    }
    
    public void createTriggers() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement stmt = conn.createStatement();
            
            String checkLogsTable = "SELECT to_regclass('public.Logs')";
            ResultSet rs = stmt.executeQuery(checkLogsTable);
            if (rs.next() && rs.getString(1) != null) {
                // Если таблица Logs существует, выходим из функции
                System.out.println("Logs table already exists, triggers are already created.");
                return;
            }

            // Создание таблицы Logs
            String createLogsTable = "CREATE TABLE IF NOT EXISTS Logs (" +
                    "log_id SERIAL PRIMARY KEY, " +
                    "action TEXT NOT NULL, " +
                    "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");";
            stmt.executeUpdate(createLogsTable);

            // Функция для регистрации пользователя
            String logUserRegistration = "CREATE OR REPLACE FUNCTION log_user_registration() RETURNS TRIGGER AS $$ " +
                    "BEGIN " +
                    "   INSERT INTO Logs (action) VALUES ('User registered: ' || NEW.client_id); " +
                    "   RETURN NEW; " +
                    "END; $$ LANGUAGE plpgsql;";
            stmt.executeUpdate(logUserRegistration);

            // Триггер для регистрации пользователя
            String createTriggerUserRegistration = "CREATE TRIGGER trg_user_registration " +
                    "AFTER INSERT ON clients " +
                    "FOR EACH ROW " +
                    "EXECUTE FUNCTION log_user_registration();";
            stmt.executeUpdate(createTriggerUserRegistration);

            // Функция для создания заказа
            String logOrderCreation = "CREATE OR REPLACE FUNCTION log_order_creation() RETURNS TRIGGER AS $$ " +
                    "BEGIN " +
                    "   INSERT INTO Logs (action) VALUES ('Order created: ' || NEW.order_id); " +
                    "   RETURN NEW; " +
                    "END; $$ LANGUAGE plpgsql;";
            stmt.executeUpdate(logOrderCreation);

            // Триггер для создания заказа
            String createTriggerOrderCreation = "CREATE TRIGGER trg_order_creation " +
                    "AFTER INSERT ON orders " +
                    "FOR EACH ROW " +
                    "EXECUTE FUNCTION log_order_creation();";
            stmt.executeUpdate(createTriggerOrderCreation);

            // Функция для создания отзыва
            String logReviewCreation = "CREATE OR REPLACE FUNCTION log_review_creation() RETURNS TRIGGER AS $$ " +
                    "BEGIN " +
                    "   INSERT INTO Logs (action) VALUES ('Review created: ' || NEW.review_id); " +
                    "   RETURN NEW; " +
                    "END; $$ LANGUAGE plpgsql;";
            stmt.executeUpdate(logReviewCreation);

            // Триггер для создания отзыва
            String createTriggerReviewCreation = "CREATE TRIGGER trg_review_creation " +
                    "AFTER INSERT ON reviews " +
                    "FOR EACH ROW " +
                    "EXECUTE FUNCTION log_review_creation();";
            stmt.executeUpdate(createTriggerReviewCreation);

            // Функция для добавления автомобиля
            String logCarAddition = "CREATE OR REPLACE FUNCTION log_car_addition() RETURNS TRIGGER AS $$ " +
                    "BEGIN " +
                    "   INSERT INTO Logs (action) VALUES ('Car added: ' || NEW.car_id); " +
                    "   RETURN NEW; " +
                    "END; $$ LANGUAGE plpgsql;";
            stmt.executeUpdate(logCarAddition);

            // Триггер для добавления автомобиля
            String createTriggerCarAddition = "CREATE TRIGGER trg_car_addition " +
                    "AFTER INSERT ON cars " +
                    "FOR EACH ROW " +
                    "EXECUTE FUNCTION log_car_addition();";
            stmt.executeUpdate(createTriggerCarAddition);

            // Функция для удаления пользователя
            String logUserDeletion = "CREATE OR REPLACE FUNCTION log_user_deletion() RETURNS TRIGGER AS $$ " +
                    "BEGIN " +
                    "   INSERT INTO Logs (action) VALUES ('User deleted: ' || OLD.client_id); " +
                    "   RETURN OLD; " +
                    "END; $$ LANGUAGE plpgsql;";
            stmt.executeUpdate(logUserDeletion);

            // Триггер для удаления пользователя
            String createTriggerUserDeletion = "CREATE TRIGGER trg_user_deletion " +
                    "AFTER DELETE ON clients " +
                    "FOR EACH ROW " +
                    "EXECUTE FUNCTION log_user_deletion();";
            stmt.executeUpdate(createTriggerUserDeletion);

            // Функция для удаления отзыва
            String logReviewDeletion = "CREATE OR REPLACE FUNCTION log_review_deletion() RETURNS TRIGGER AS $$ " +
                    "BEGIN " +
                    "   INSERT INTO Logs (action) VALUES ('Review deleted: ' || OLD.review_id); " +
                    "   RETURN OLD; " +
                    "END; $$ LANGUAGE plpgsql;";
            stmt.executeUpdate(logReviewDeletion);

            // Триггер для удаления отзыва
            String createTriggerReviewDeletion = "CREATE TRIGGER trg_review_deletion " +
                    "AFTER DELETE ON reviews " +
                    "FOR EACH ROW " +
                    "EXECUTE FUNCTION log_review_deletion();";
            stmt.executeUpdate(createTriggerReviewDeletion);

            // Функция для удаления заказа
            String logOrderDeletion = "CREATE OR REPLACE FUNCTION log_order_deletion() RETURNS TRIGGER AS $$ " +
                    "BEGIN " +
                    "   INSERT INTO Logs (action) VALUES ('Order deleted: ' || OLD.order_id); " +
                    "   RETURN OLD; " +
                    "END; $$ LANGUAGE plpgsql;";
            stmt.executeUpdate(logOrderDeletion);

            // Триггер для удаления заказа
            String createTriggerOrderDeletion = "CREATE TRIGGER trg_order_deletion " +
                    "AFTER DELETE ON orders " +
                    "FOR EACH ROW " +
                    "EXECUTE FUNCTION log_order_deletion();";
            stmt.executeUpdate(createTriggerOrderDeletion);
            
            String logOrderBeforeCarDeletion = "CREATE OR REPLACE FUNCTION delete_orders_for_car() "+ 
            		"RETURNS TRIGGER AS $$" + " BEGIN " + "DELETE FROM orders WHERE car_id = OLD.car_id;" 
            		+ " RETURN OLD; " + "END;" + " $$ LANGUAGE plpgsql;";
            stmt.executeUpdate(logOrderBeforeCarDeletion);
            
            String CreateTriggerOrderDeletingBeforeCar = "CREATE TRIGGER before_delete_car" +
            		" BEFORE DELETE ON cars" + " FOR EACH ROW" + " EXECUTE FUNCTION delete_orders_for_car();";
            stmt.executeUpdate(CreateTriggerOrderDeletingBeforeCar);
            
            String revorddel = "CREATE OR REPLACE FUNCTION delete_reviews_for_order() "+
            		"RETURNS TRIGGER AS $$ "+
            		"BEGIN "+
            		    "DELETE FROM reviews WHERE order_id = OLD.order_id; "+
            		    "RETURN OLD; "+
            		"END; "+
            		"$$ LANGUAGE plpgsql;";
            stmt.executeUpdate(revorddel);
            
            String revorddeltrig = "CREATE TRIGGER before_delete_order "+
            		"BEFORE DELETE ON orders "+
            		"FOR EACH ROW "+
            		"EXECUTE FUNCTION delete_reviews_for_order();";
            stmt.executeUpdate(revorddeltrig);

            System.out.println("Triggers successfully created!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    /*public void createTables() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement stmt = conn.createStatement();

            // Проверяем, существует ли таблица clients
            String checkClientsTable = "SELECT to_regclass('public.clients')";
            if (tableExists(stmt, checkClientsTable)) {
                System.out.println("Tables already exist, skipping creation.");
                return;
            }

            // Создание таблицы clients
            String createClientsTable = "CREATE TABLE clients (" +
                    "client_id SERIAL PRIMARY KEY, " +
                    "login VARCHAR(50) NOT NULL, " +
                    "password INTEGER NOT NULL, " +
                    "bank_card VARCHAR(20), " +
                    "phone_number VARCHAR(20), " +
                    "orders_count INTEGER" +
                    ");";
            stmt.executeUpdate(createClientsTable);

            // Создание таблицы admins
            String createAdminsTable = "CREATE TABLE admins (" +
                    "admin_id SERIAL PRIMARY KEY, " +
                    "login VARCHAR(50) NOT NULL, " +
                    "password INTEGER NOT NULL" +
                    ");";
            stmt.executeUpdate(createAdminsTable);

            // Создание таблицы departments
            String createDepartmentsTable = "CREATE TABLE departments (" +
                    "depart_id SERIAL PRIMARY KEY, " +
                    "adres VARCHAR(200), " +
                    "work_time VARCHAR(50), " +
                    "phone_number VARCHAR(20)" +
                    ");";
            stmt.executeUpdate(createDepartmentsTable);

            // Создание таблицы cars
            String createCarsTable = "CREATE TABLE cars (" +
                    "car_id SERIAL PRIMARY KEY, " +
                    "model VARCHAR(100), " +
                    "price NUMERIC(10, 2), " +
                    "depart_id INTEGER REFERENCES departments(depart_id), " +
                    "state INTEGER" +
                    ");";
            stmt.executeUpdate(createCarsTable);

            // Создание таблицы admins_departs
            String createAdminsDepartsTable = "CREATE TABLE admins_departs (" +
                    "admin_depart_id SERIAL PRIMARY KEY, " +
                    "admin_id INTEGER REFERENCES admins(admin_id), " +
                    "depart_id INTEGER REFERENCES departments(depart_id)" +
                    ");";
            stmt.executeUpdate(createAdminsDepartsTable);

            // Создание таблицы orders
            String createOrdersTable = "CREATE TABLE orders (" +
                    "order_id SERIAL PRIMARY KEY, " +
                    "client_id INTEGER REFERENCES clients(client_id), " +
                    "car_id INTEGER REFERENCES cars(car_id), " +
                    "state INTEGER" +
                    ");";
            stmt.executeUpdate(createOrdersTable);

            // Создание таблицы reviews
            String createReviewsTable = "CREATE TABLE reviews (" +
                    "review_id SERIAL PRIMARY KEY, " +
                    "order_id INTEGER REFERENCES orders(order_id), " +
                    "text TEXT" +
                    ");";
            stmt.executeUpdate(createReviewsTable);

            System.out.println("Tables successfully created!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean tableExists(Statement stmt, String checkQuery) throws SQLException {
        try (ResultSet rs = stmt.executeQuery(checkQuery)) {
            if (rs.next() && rs.getString(1) != null) {
                return true;
            }
        }
        return false;
    }*/
}



