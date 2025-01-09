<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 09/01/2025
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resumen Clientes por Comercial</title>
</head>
<body>

<h1>Resumen de Clientes por Comercial</h1>
<h1>Resumen de Clientes por Comercial</h1>

<!-- Tabla con resumen de clientes por comercial -->
<!--Cada comercial te lo asocia con los listados Pedidos-->
<table border="1">
    <thead>
    <tr>
        <th>Comercial</th>
        <th>Clientes Asociados</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="entry" items="${resumen}">
        <tr>
            <td>${entry.nombre} ${entry.apellido}</td>
            <td>${entry.numeroClientes}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<!--NO SON LOS BUENOS: te meten mas valores como nombre y apellido-->
<!-- Tabla con resumen de clientes por comercial -->
<table border="1">
    <thead>
    <tr>
        <th>Comercial</th>
        <th>Clientes Asociados</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="comercial" items="${comerciales}">
        <tr>
            <td>${comercial.nombre} ${comercial.apellido1}</td>
            <td>
                <c:forEach var="cliente" items="${comercial.clientes}">
                    <p>${cliente.nombre} ${cliente.apellido1} ${cliente.apellido2}</p>
                </c:forEach>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<hr>

<!-- Listado de pedidos -->
<h2>Listado de Pedidos</h2>
<!-- Aquí el listado de pedidos se incluirá con un requestDispatcher desde el servlet de pedidos -->
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
