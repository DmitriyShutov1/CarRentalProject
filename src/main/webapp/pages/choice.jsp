<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Выбор действия</title>
    <style>
        body {
            background-color: #ADD8E6; /* Светло-голубой цвет */
        }
    </style>
</head>
<body>
    <h1>Выберите действие</h1>
    <form action="<%= request.getContextPath() %>/pages/availableCars.jsp">
        <button type="submit">Доступные автомобили</button>
    </form>
    <form action="<%= request.getContextPath() %>/pages/myOrders.jsp">
        <button type="submit">Мои заказы</button>
    </form>
    <form action="<%= request.getContextPath() %>/pages/login.jsp">
        <button type="submit">Выйти</button>
    </form>
    <img src="<%= request.getContextPath() %>/pictures/gtr.png" alt="Example Image" style="max-width: 100%; height: auto; margin-top: 20px;">
</body>
</html>
