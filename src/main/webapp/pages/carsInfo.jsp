<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.Car" %>
<!DOCTYPE html>
<html>
<head>
    <title>Информация об автомобилях</title>
    <style>
        body {
            background-color: #FFB6C1; /* Светло-голубой цвет */
        }
    </style>
</head>
<body>
    <h1>Информация об автомобилях</h1>
    <form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="admincars">
        <input type="submit" value="Обновить">
    </form>
    <table border="1">
        <tr>
            <th>Car ID</th>
            <th>Model</th>
            <th>Price</th>
            <th>Depart ID</th>
            <th>State</th>
            <th>Department Address</th>
        </tr>
        <%
            List<Car> cars = (List<Car>) request.getAttribute("cars");
            if (cars != null) {
                for (Car car : cars) {
        %>
        <tr>
            <td><%= car.getCarId() %></td>
            <td><%= car.getModel() %></td>
            <td><%= car.getPrice() %></td>
            <td><%= car.getDepartId() %></td>
            <td><%= car.getState() %></td>
            <td><%= car.getDepartmentAddress() %></td>
        </tr>
        <%
                }
            }
        %>
    </table>
    
    <h2>Изменить данные:</h2>
    <form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="updatecar">
        <label for="carId">Id автомобиля:</label>
        <input type="text" id="carId" name="carId" required><br>
        <label for="newPrice">Новая цена:</label>
        <input type="text" id="newPrice" name="newPrice" required><br>
        <label for="newState">Новое состояние (готовность к аренде):</label>
        <input type="text" id="newState" name="newState" required><br>
        <input type="submit" value="Изменить">
    </form>

    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
    <p><%= message %></p>
    <%
        }
    %>
    <form action="<%= request.getContextPath() %>/auth" method="post" style="display:inline;">
	    <input type="hidden" name="action" value="toMenu">
	    <button type="submit">Вернуться в меню</button>
	</form>
</body>
</html>