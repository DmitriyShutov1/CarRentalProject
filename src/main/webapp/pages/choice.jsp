<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Выбор действия</title>
    <style>
        /* Основные стили */
        body {
            background: linear-gradient(135deg, #1e90ff, #87ceeb); /* Градиентный фон */
            font-family: 'Arial', sans-serif;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            color: #fff;
        }

        h1 {
            font-size: 2.5rem;
            margin-bottom: 20px;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3); /* Тень текста */
        }

        /* Контейнер для кнопок */
        .button-container {
            display: flex;
            flex-direction: column;
            gap: 20px;
            margin-bottom: 30px;
        }

        /* Стили для кнопок */
        .button-container button {
            background: linear-gradient(135deg, #ff6347, #ffa500); /* Градиентный цвет */
            color: #fff;
            border: none;
            padding: 15px 30px;
            font-size: 1.2rem;
            border-radius: 10px;
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2); /* Тень */
        }

        .button-container button:hover {
            transform: translateY(-5px); /* Поднятие кнопки при наведении */
            box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3); /* Увеличение тени */
        }

        .button-container button:active {
            transform: translateY(0); /* Возврат кнопки в исходное положение при нажатии */
            box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2); /* Уменьшение тени */
        }

        /* Изображение */
        img {
            max-width: 300px;
            border-radius: 15px;
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3); /* Тень для изображения */
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        img:hover {
            transform: scale(1.05); /* Увеличение изображения при наведении */
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.4); /* Увеличение тени */
        }

        /* Отзывчивость */
        @media (max-width: 768px) {
            h1 {
                font-size: 2rem;
            }

            .button-container button {
                padding: 10px 20px;
                font-size: 1rem;
            }

            img {
                max-width: 200px;
            }
        }
    </style>
</head>
<body>
    <h1>Выберите действие</h1>
    <div class="button-container">
        <form action="<%= request.getContextPath() %>/pages/availableCars.jsp">
            <button type="submit">Доступные автомобили</button>
        </form>
        <form action="<%= request.getContextPath() %>/pages/myOrders.jsp">
            <button type="submit">Мои заказы</button>
        </form>
        <form action="<%= request.getContextPath() %>/pages/login.jsp">
            <button type="submit">Выйти</button>
        </form>
    </div>
    <img src="<%= request.getContextPath() %>/pictures/gtr.png" alt="Example Image">
</body>
</html>