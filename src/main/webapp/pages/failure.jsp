<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ошибка</title>
    <style>
        body {
            background: linear-gradient(135deg, #dc3545, #ff6347); /* Красный градиентный фон */
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

        button {
            background: linear-gradient(135deg, #1e90ff, #87ceeb); /* Градиентный цвет */
            color: #fff;
            border: none;
            padding: 15px 30px;
            font-size: 1.2rem;
            border-radius: 10px;
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2); /* Тень */
        }

        button:hover {
            transform: translateY(-5px); /* Поднятие кнопки при наведении */
            box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3); /* Увеличение тени */
        }

        button:active {
            transform: translateY(0); /* Возврат кнопки в исходное положение при нажатии */
            box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2); /* Уменьшение тени */
        }
    </style>
</head>
<body>
    <h1>Операция завершилась ошибкой.</h1>
    <form action="<%= request.getContextPath() %>/pages/login.jsp">
        <button type="submit">Выйти</button>
    </form>
</body>
</html>