<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Добавить или удалить автомобиль</title>
    <style>
        body {
            background-color: #FFB6C1; /* Светло-голубой цвет */
        }
    </style>
</head>
<body>
    <h1>Добавить автомобиль</h1>
    <form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="addCar">
        <label for="model">Модель:</label>
        <input type="text" id="model" name="model" required><br>
        <label for="price">Цена:</label>
        <input type="text" id="price" name="price" required><br>
        <input type="submit" value="Добавить">
    </form>
    <h1>Удалить автомобиль</h1>
    <form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="deleteCar">
        <label for="carId">ID автомобиля:</label>
        <input type="text" id="carId" name="carId" required><br>
        <input type="submit" value="Удалить">
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