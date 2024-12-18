<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Успех</title>
    
</head>
<body>
    <h1>Операция выполнена успешно!</h1>
    <form action="<%= request.getContextPath() %>/pages/login.jsp">
        <button type="submit">Выйти</button>
    </form>
</body>
</html>
