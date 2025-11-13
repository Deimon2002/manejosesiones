<%--
  Created by IntelliJ IDEA.
  User: Thede
  Date: 12/11/2025
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login de Usuario</title>
</head>
<body>
<h1>Iniciar Sesión</h1>

<form action="${pageContext.request.contextPath}/login.html" method="post">
    <div>
        <label for="user">Usuario:</label>
        <input type="text" id="user" name="user">
    </div>
    <div>
        <label for="password">Contraseña:</label>
        <input type="password" id="password" name="password">
    </div>
    <div>
        <input type="submit" value="Ingresar">
    </div>
</form>
</body>
</html>