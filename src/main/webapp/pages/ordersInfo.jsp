<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.Review" %>
<!DOCTYPE html>
<html>
<head>
    <title>Информация о заказах</title>
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
    <h1>Информация о заказах</h1>
    <form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="showrevs">
        <input type="submit" value="Обновить">
    </form>
    <table border="1">
        <tr>
            <th>Client ID</th>
            <th>Car Model</th>
            <th>Review</th>
        </tr>
        <%
            List<Review> reviews = (List<Review>) request.getAttribute("reviews");
            if (reviews != null) {
                for (Review review : reviews) {
        %>
        <tr>
            <td><%= review.getClientId() %></td>
            <td><%= review.getCarModel() %></td>
            <td><%= review.getReviewText() %></td>
        </tr>
        <%
                }
            }
        %>
    </table>
    
    <h2>Забанить:</h2>
    <form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="ban">
        <label for="clientId">ID клиента:</label>
        <input type="text" id="clientId" name="clientId" required>
        <input type="submit" value="Забанить">
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