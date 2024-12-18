<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
    <style>
        body {
            background: linear-gradient(135deg, #1e90ff, #87ceeb); /* Градиентный фон */
            font-family: 'Arial', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .registration-container {
            background: #fff;
            padding: 20px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            text-align: center;
            width: 300px;
        }
        .registration-container h1 {
            color: #333;
            margin-bottom: 20px;
        }
        .registration-container label {
            display: block;
            text-align: left;
            margin: 10px 0 5px;
            color: #555;
        }
        .registration-container input[type="text"],
        .registration-container input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }
        .registration-container input[type="submit"] {
            background: #1e90ff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background 0.3s ease;
        }
        .registration-container input[type="submit"]:hover {
            background: #0077cc;
        }
    </style>
</head>
<body>
    <div class="registration-container">
        <h1>Регистрация</h1>
        <form action="<%= request.getContextPath() %>/auth" method="post">
            <input type="hidden" name="action" value="reg">
            <label for="login">Логин:</label>
            <input type="text" id="login" name="login" required>
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required>
            <label for="phone">Телефон:</label>
            <input type="text" id="phone" name="phone" required>
            <label for="card">Номер карты:</label>
            <input type="text" id="card" name="card" required>
            <input type="submit" value="Зарегистрироваться">
        </form>
    </div>
</body>
</html>