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
            background: linear-gradient(135deg, #1e90ff, #87ceeb); /* Градиентный фон */
            font-family: 'Arial', sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
            color: #fff;
            padding: 20px;
        }

        h1 {
            font-size: 2.5rem;
            margin-bottom: 20px;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3); /* Тень текста */
        }

        button {
            background: linear-gradient(135deg, #ff6347, #ffa500); /* Градиентный цвет */
            color: #fff;
            border: none;
            padding: 10px 20px;
            font-size: 1rem;
            border-radius: 10px;
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2); /* Тень */
            margin-bottom: 20px;
        }

        button:hover {
            transform: translateY(-5px); /* Поднятие кнопки при наведении */
            box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3); /* Увеличение тени */
        }

        ul {
            list-style: none;
            padding: 0;
            width: 100%;
            max-width: 600px;
        }

        li {
            background: #fff;
            color: #333;
            padding: 15px;
            margin-bottom: 10px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2); /* Тень */
            animation: fadeIn 0.5s ease; /* Анимация появления */
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        p {
            color: #ff6347;
            font-weight: bold;
        }

        input[type="number"] {
            padding: 10px;
            margin-top: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1rem;
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
                <li>
                    ID: <%= car.getCarId() %>, Модель: <%= car.getModel() %>, Цена: <%= car.getPrice() %>, Адрес департамента: <%= car.getDepartmentAddress() %>
                </li>
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