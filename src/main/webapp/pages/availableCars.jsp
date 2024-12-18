<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.example.Car" %>
<%@ page import="java.util.List" %>
<%
    // Получаем список автомобилей из атрибута запроса
    List<Car> cars = (List<Car>) request.getAttribute("cars");
    String message = (String) request.getAttribute("message");
%>

<html>
<head>
    <title>Доступные автомобили</title>
    <style>
        body {
            background-color: #ADD8E6; /* Светло-голубой цвет */
        }
    </style>
</head>
<body>

	<form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="refreshCars">
        <button type="submit">Обновить</button>
    </form>

    <h1>Доступные автомобили</h1>
    <% if (message != null) { %>
        <p><%= message %></p>
    <% } %>

    <ul>
        <% if (cars != null && !cars.isEmpty()) { %>
            <% for (Car car : cars) { %>
                <li>ID: <%= car.getCarId() %>, Модель: <%= car.getModel() %>, Цена: <%= car.getPrice() %>, Адрес департамента: <%= car.getDepartmentAddress() %></li>
            <% } %>
        <% } else { %>
            <p>Нет доступных автомобилей.</p>
        <% } %>
    </ul>

    <h2>Бронирование автомобиля</h2>
    <form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="rent">
        <label for="carId">Введите ID автомобиля:</label>
        <input type="number" name="carId" required>
        <button type="submit">Забронировать</button>
    </form>
    <form action="<%= request.getContextPath() %>/pages/choice.jsp">
        <button type="submit">Меню</button>
    </form>
</body>
</html>

