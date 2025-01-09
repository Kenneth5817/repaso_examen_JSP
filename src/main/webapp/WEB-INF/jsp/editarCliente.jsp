
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Editar Cliente</title>
</head>
<body>
<h1>Editar Cliente</h1>
<form method="post" action="/clientes/update">
    <input type="hidden" name="id" value="${cliente.id}">

    <label for="nombre">Nombre:</label>
    <input type="text" name="nombre" id="nombre" value="${cliente.nombre}" required><br>

    <label for="apellido1">Apellido 1:</label>
    <input type="text" name="apellido1" id="apellido1" value="${cliente.apellido1}" required><br>

    <label for="apellido2">Apellido 2:</label>
    <input type="text" name="apellido2" id="apellido2" value="${cliente.apellido2}"><br>

    <label for="ciudad">Ciudad:</label>
    <input type="text" name="ciudad" id="ciudad" value="${cliente.ciudad}"><br>

    <label for="categoria">Categoría:</label>
    <input type="number" name="categoria" id="categoria" value="${cliente.categoria}" required><br>

    <button type="submit">Guardar Cambios</button>
</form>

</body>
</html>

