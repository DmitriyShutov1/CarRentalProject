<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ошибка</title>
    <style>
        body {
            background-color: #FFB6C1; /* Светло-голубой цвет */
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