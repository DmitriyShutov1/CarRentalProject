<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Меню админа</title>
    <style>
        body {
            background-color: #FFB6C1; /* Светло-голубой цвет */
        }
    </style>
</head>
<body>
    <h1>Меню</h1>

    <form action="<%= request.getContextPath() %>/pages/addCar.jsp">
        <button type="submit">Добавить или удалить автомобиль</button>
    </form>
    <form action="<%= request.getContextPath() %>/pages/carsInfo.jsp">
        <button type="submit">Информация об автомобилях</button>
    </form>
    <form action="<%= request.getContextPath() %>/pages/ordersInfo.jsp">
        <button type="submit">Информация о заказах</button>
    </form>
    <form action="<%= request.getContextPath() %>/pages/login.jsp">
        <button type="submit">Выйти</button>
    </form>
</body>
</html>


