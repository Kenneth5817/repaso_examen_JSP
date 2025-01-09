<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 09/01/2025
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buscar Pedido por Rango de Cantidad</title>
</head>
<body>

<h1>Buscar Pedido por Rango de Cantidad</h1>

<!-- Formulario para buscar pedidos por rango de cantidad -->
<form action="${pageContext.request.contextPath}/buscarPedidosPorRango" method="GET">
    <label for="cantidadMin">Cantidad Mínima: </label>
    <input type="number" name="cantidadMin" id="cantidadMin" step="0.01" required><br>

    <label for="cantidadMax">Cantidad Máxima: </label>
    <input type="number" name="cantidadMax" id="cantidadMax" step="0.01" required><br>

    <button type="submit">Buscar Pedidos</button>
</form>

<hr>

<!-- Resultados de la búsqueda -->
<h2>Resultados de la Búsqueda</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID Pedido</th>
        <th>Total</th>
        <th>Fecha</th>
        <th>Cliente</th>
        <th>Comercial</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="pedido" items="${pedidos}">
        <tr>
            <td>${pedido.id}</td>
            <td>${pedido.total}</td>
            <td>${pedido.fecha}</td>
            <td>${pedido.cliente.nombre} ${pedido.cliente.apellido1}</td>
            <td>${pedido.comercial.nombre} ${pedido.comercial.apellido1}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>

