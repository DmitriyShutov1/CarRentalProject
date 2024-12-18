<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.Car" %>
<!DOCTYPE html>
<html>
<head>
    <title>Информация об автомобилях</title>
    <style>
        body {
            background: linear-gradient(135deg, #ff6347, #ffa500); /* Градиентный фон */
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

        h2 {
            font-size: 1.8rem;
            margin-top: 30px;
            margin-bottom: 20px;
        }

        form {
            margin-bottom: 20px;
        }

        input[type="submit"] {
            background: linear-gradient(135deg, #1e90ff, #87ceeb); /* Градиентный цвет */
            color: #fff;
            border: none;
            padding: 10px 20px;
            font-size: 1rem;
            border-radius: 10px;
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2); /* Тень */
        }

        input[type="submit"]:hover {
            transform: translateY(-5px); /* Поднятие кнопки при наведении */
            box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3); /* Увеличение тени */
        }

        table {
            width: 100%;
            max-width: 800px;
            border-collapse: collapse;
            margin-bottom: 30px;
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

        th, td {
            padding: 10px;
            text-align: center;
            border: 1px solid #ccc;
            background: #fff;
            color: #333;
        }

        th {
            background: linear-gradient(135deg, #1e90ff, #87ceeb); /* Градиентный цвет */
            color: #fff;
            font-weight: bold;
        }

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1rem;
        }

        p {
            color: #ff6347;
            font-weight: bold;
        }

        .menu-button {
            background: linear-gradient(135deg, #1e90ff, #87ceeb); /* Градиентный цвет */
            color: #fff;
            border: none;
            padding: 10px 20px;
            font-size: 1rem;
            border-radius: 10px;
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2); /* Тень */
            margin-top: 20px;
        }

        .menu-button:hover {
            transform: translateY(-5px); /* Поднятие кнопки при наведении */
            box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3); /* Увеличение тени */
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
	    <button type="submit" class="menu-button">Вернуться в меню</button>
	</form>
</body>
</html>