package com.example;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClientDAO clientDAO;
    private CarDAO carDAO;
    private AdminDAO adminDAO;
    private OrderDAO orderDAO;
    private ReviewDAO reviewDAO;
    private ExecutorService executorService;

    @Override
    public void init() throws ServletException {
        clientDAO = new ClientDAO();
        carDAO = new CarDAO();
        adminDAO = new AdminDAO();
        orderDAO = new OrderDAO();
        reviewDAO = new ReviewDAO();
        executorService = Executors.newFixedThreadPool(10);
    }

    @Override
    public void destroy() {
        executorService.shutdown();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("auth".equals(action)) {
            handleLogin(request, response);
        } else if ("reg".equals(action)) {
            handleRegistration(request, response);
        } else if ("rent".equals(action)) {
            handleCarRent(request, response);
        } else if ("refreshCars".equals(action)) {
            showAvailableCars(request, response);
        } else if ("myorders".equals(action)) {
            showMyOrders(request, response);
        } else if ("review".equals(action)) {
            handleReview(request, response);
        } else if ("showrevs".equals(action)) {
            showReviews(request, response);
        } else if ("ban".equals(action)) {
            deleteClient(request, response);
        } else if ("admincars".equals(action)) {
            showCarsToAdmin(request, response);
        } else if ("updatecar".equals(action)) {
            updateCar(request, response);
        } else if ("addCar".equals(action)) {
            addCar(request, response);
        } else if("deleteCar".equals(action)){
        	deleteCar(request, response);
        } else if("toMenu".equals(action)){
        	toMenu(request, response);
        }else {
            response.sendRedirect("pages/failure.jsp");
        }
    }
    
    private void toMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Перенаправляем пользователя на страницу adminMenu.jsp
        response.sendRedirect("pages/adminMenu.jsp");
    }
    
    private void deleteCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int carId = Integer.parseInt(request.getParameter("carId"));

        Future<Boolean> future = executorService.submit(() -> carDAO.deleteCar(carId));

        try {
            boolean deleted = future.get();
            if (deleted) {
                request.setAttribute("message", "Автомобиль с ID " + carId + " успешно удален.");
            } else {
                request.setAttribute("message", "Ошибка при удалении автомобиля с ID " + carId + ".");
            }

            //showCarsToAdmin(request, response); // Обновляем список автомобилей
            RequestDispatcher dispatcher = request.getRequestDispatcher("pages/addCar.jsp");
            dispatcher.forward(request, response);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            response.sendRedirect("pages/failure.jsp");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        int passwordHash = password.hashCode();
        int role = Integer.parseInt(request.getParameter("role"));

        // Используем Callable для задания корректного типа
        Future<Integer> future = executorService.submit(() -> {
            if (role == 1) { // Если администратор
                return adminDAO.authenticateAdmin(login, passwordHash);
            } else { // Если клиент
                return clientDAO.authenticateClient(login, passwordHash);
            }
        });

        try {
            Integer userId = future.get(); // Получаем результат выполнения задачи
            if (userId != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("role", role);

                if (role == 1) { // Администратор
                    request.getRequestDispatcher("pages/adminMenu.jsp").forward(request, response);
                } else { // Клиент
                    request.getRequestDispatcher("pages/choice.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("pages/failure.jsp").forward(request, response);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            response.sendRedirect("pages/failure.jsp");
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        int passwordHash = password.hashCode();
        String phone = request.getParameter("phone");
        String card = request.getParameter("card");

        Future<Boolean> future = executorService.submit(() -> clientDAO.registerClient(login, passwordHash, phone, card));

        try {
            if (future.get()) {
                request.getRequestDispatcher("pages/success.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("pages/failure.jsp").forward(request, response);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            response.sendRedirect("pages/failure.jsp");
        }
    }

    private void handleCarRent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("pages/failure.jsp");
            return;
        }

        int carId = Integer.parseInt(request.getParameter("carId"));

        Future<Boolean> rentFuture = executorService.submit(() -> carDAO.rentCar(carId, userId));
        Future<Boolean> orderFuture = executorService.submit(() -> new OrderDAO().createOrder(userId, carId));

        try {
            boolean rentSuccessful = rentFuture.get();
            if (rentSuccessful) {
                boolean orderCreated = orderFuture.get();
                if (orderCreated) {
                    request.setAttribute("message", "Автомобиль забронирован и заказ создан!");
                } else {
                    request.setAttribute("message", "Ошибка создания заказа. Попробуйте снова.");
                }
            } else {
                request.setAttribute("message", "Ошибка бронирования. Попробуйте снова.");
            }

            showAvailableCars(request, response);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ошибка при обработке запроса. Попробуйте снова.");
            showAvailableCars(request, response);
        }
    }

    private void showAvailableCars(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем список доступных автомобилей
        Future<List<Car>> future = executorService.submit(() -> carDAO.getAvailableCars());

        try {
            List<Car> availableCars = future.get();
            request.setAttribute("cars", availableCars);

            RequestDispatcher dispatcher = request.getRequestDispatcher("pages/availableCars.jsp");
            dispatcher.forward(request, response);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            response.sendRedirect("pages/failure.jsp");
        }
    }

    private void showReviews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Future<List<Review>> future = executorService.submit(() -> reviewDAO.getAllReviews());

        try {
            List<Review> reviews = future.get();
            request.setAttribute("reviews", reviews);

            RequestDispatcher dispatcher = request.getRequestDispatcher("pages/ordersInfo.jsp");
            dispatcher.forward(request, response);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            response.sendRedirect("pages/failure.jsp");
        }
    }

    private void deleteClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("clientId"));

        Future<Boolean> future = executorService.submit(() -> clientDAO.deleteClient(clientId));

        try {
            boolean deleted = future.get();
            if (deleted) {
                request.setAttribute("message", "Клиент с ID " + clientId + " успешно удален.");
            } else {
                request.setAttribute("message", "Ошибка при удалении клиента с ID " + clientId + ".");
            }

            showReviews(request, response); // Обновляем список отзывов
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            response.sendRedirect("pages/failure.jsp");
        }
    }

    private void showCarsToAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("userId");

        if (adminId == null) {
            response.sendRedirect("pages/failure.jsp");
            return;
        }

        Future<List<Car>> future = executorService.submit(() -> carDAO.showCarsToAdmin(adminId));

        try {
            List<Car> cars = future.get();
            request.setAttribute("cars", cars);
            RequestDispatcher dispatcher = request.getRequestDispatcher("pages/carsInfo.jsp");
            dispatcher.forward(request, response);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            response.sendRedirect("pages/failure.jsp");
        }
    }

    private void updateCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int carId = Integer.parseInt(request.getParameter("carId"));
        double newPrice = Double.parseDouble(request.getParameter("newPrice"));
        int newState = Integer.parseInt(request.getParameter("newState"));

        Future<Boolean> future = executorService.submit(() -> carDAO.updateCar(carId, newPrice, newState));

        try {
            boolean updated = future.get();
            if (updated) {
                request.setAttribute("message", "Данные автомобиля с ID " + carId + " успешно обновлены.");
            } else {
                request.setAttribute("message", "Ошибка при обновлении данных автомобиля с ID " + carId + ".");
            }

            showCarsToAdmin(request, response); // Обновляем список автомобилей
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            response.sendRedirect("pages/failure.jsp");
        }
    }

    private void addCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String model = request.getParameter("model");
        double price = Double.parseDouble(request.getParameter("price"));

        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("userId");

        if (adminId == null) {
            response.sendRedirect("pages/failure.jsp");
            return;
        }

        Future<Boolean> future = executorService.submit(() -> carDAO.addCar(model, price, adminId));

        try {
            boolean added = future.get();
            if (added) {
                request.setAttribute("message", "Автомобиль успешно добавлен.");
            } else {
                request.setAttribute("message", "Ошибка при добавлении автомобиля.");
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("pages/addCar.jsp");
            dispatcher.forward(request, response);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            response.sendRedirect("pages/failure.jsp");
        }
    }

    
    private void handleReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("pages/failure.jsp");
            return;
        }

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String review = request.getParameter("review");

        // Используем ExecutorService для асинхронного выполнения
        Future<Boolean> reviewSavedFuture = executorService.submit(() -> reviewDAO.saveReview(orderId, review));
        Future<Boolean> orderCompletedFuture = executorService.submit(() -> orderDAO.completeOrder(orderId));

        try {
            boolean reviewSaved = reviewSavedFuture.get(); // Получаем результат выполнения задачи
            boolean orderCompleted = orderCompletedFuture.get(); // Получаем результат выполнения задачи

            if (reviewSaved && orderCompleted) {
                request.setAttribute("message", "Заказ завершен и отзыв оставлен!");
            } else {
                request.setAttribute("message", "Ошибка при завершении заказа. Попробуйте снова.");
            }

            // После завершения обработки отзывов и заказов, обновляем список заказов
            showMyOrders(request, response);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ошибка при завершении заказа. Попробуйте снова.");
            showMyOrders(request, response); // В случае ошибки показываем список заказов
        }
    }

    private void showMyOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("pages/failure.jsp");
            return;
        }

        // Используем ExecutorService для асинхронного выполнения
        Future<List<Order>> ordersFuture = executorService.submit(() -> orderDAO.getClientsOrders(userId));

        try {
            // Получаем список заказов асинхронно
            List<Order> orders = ordersFuture.get();

            // Передаем список в атрибут запроса
            request.setAttribute("orders", orders);

            // Перенаправляем на страницу myOrders.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("pages/myOrders.jsp");
            dispatcher.forward(request, response);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ошибка при получении заказов. Попробуйте снова.");
            response.sendRedirect("pages/failure.jsp");
        }
    }
}
