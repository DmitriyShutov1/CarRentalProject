<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Добавить или удалить автомобиль</title>
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

        form {
            background: #fff;
            color: #333;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2); /* Тень */
            margin-bottom: 20px;
            width: 100%;
            max-width: 400px; /* Ограничение ширины формы */
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

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }

        input[type="text"], input[type="submit"] {
            width: 90%; /* Уменьшена ширина полей на 10% */
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1rem;
        }

        input[type="submit"] {
            background: linear-gradient(135deg, #1e90ff, #87ceeb); /* Градиентный цвет */
            color: #fff;
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        input[type="submit"]:hover {
            transform: translateY(-5px); /* Поднятие кнопки при наведении */
            box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3); /* Увеличение тени */
        }

        p {
            color: #ff6347;
            font-weight: bold;
        }

        .form-group {
            margin-bottom: 20px;
        }

        /* Стили для кнопки "Вернуться в меню" */
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
    <h1>Добавить автомобиль</h1>
    <form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="addCar">
        <div class="form-group">
            <label for="model">Модель:</label>
            <input type="text" id="model" name="model" required>
        </div>
        <div class="form-group">
            <label for="price">Цена:</label>
            <input type="text" id="price" name="price" required>
        </div>
        <input type="submit" value="Добавить">
    </form>

    <h1>Удалить автомобиль</h1>
    <form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="deleteCar">
        <div class="form-group">
            <label for="carId">ID автомобиля:</label>
            <input type="text" id="carId" name="carId" required>
        </div>
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
	    <button type="submit" class="menu-button">Вернуться в меню</button>
	</form>
</body>
</html>