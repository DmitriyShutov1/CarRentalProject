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
            background-color: #ADD8E6; /* Светло-голубой цвет */
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
                <li>ID заказа: <%= order.getOrderId() %>, Модель: <%= order.getModel() %>, Цена: <%= order.getPrice() %>
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
