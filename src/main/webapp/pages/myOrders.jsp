<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.example.Order" %>
<%@ page import="java.util.List" %>
<%
    // Получаем список заказов из атрибута запроса
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    String message = (String) request.getAttribute("message");
%>

<html>
<head>
    <title>Мои Заказы</title>
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

        textarea {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1rem;
        }

        p {
            color: #ff6347;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="myorders">
        <button type="submit">Обновить</button>
    </form>

    <h1>Мои Заказы</h1>
    <% if (message != null) { %>
        <p><%= message %></p>
    <% } %>

    <ul>
        <% if (orders != null && !orders.isEmpty()) { %>
            <% for (Order order : orders) { %>
                <li>
                    ID заказа: <%= order.getOrderId() %>, Модель: <%= order.getModel() %>, Цена: <%= order.getPrice() %>
                    <form action="<%= request.getContextPath() %>/auth" method="post">
                        <input type="hidden" name="action" value="review">
                        <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
                        <label for="review">Ваш отзыв:</label>
                        <textarea name="review" required></textarea>
                        <button type="submit">Завершить заказ</button>
                    </form>
                </li>
            <% } %>
        <% } else { %>
            <p>Нет активных заказов.</p>
        <% } %>
    </ul>
    <form action="<%= request.getContextPath() %>/pages/choice.jsp">
        <button type="submit">Меню</button>
    </form>
</body>
</html>