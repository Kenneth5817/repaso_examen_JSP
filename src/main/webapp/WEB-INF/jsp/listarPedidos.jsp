<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 09/01/2025
  Time: 0:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table>
    <thead>
    <tr>
        <th>Comercial</th>
        <th>Número de Clientes</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="comercial" items="${resumen}">
        <tr>
            <td>${comercial.nombre} ${comercial.apellido1}</td>
            <td>${comercial.numeroClientes}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Total</th>
        <th>Fecha</th>
        <th>Cliente</th>
        <th>Comercial</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="pedido" items="${pedidos}">
        <tr>
            <td>${pedido.id}</td>
            <td>${pedido.total}</td>
            <td>${pedido.fecha}</td>
            <td>${pedido.clienteNombre} ${pedido.clienteApellido1}</td>
            <td>${pedido.comercialNombre} ${pedido.comercialApellido1}</td>
            <td>
                <a href="/pedidos/editar?id=${pedido.id}">Editar</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form action="/pedidos/buscar" method="get">
    <input type="number" name="min" placeholder="Cantidad mínima">
    <input type="number" name="max" placeholder="Cantidad máxima">
    <button type="submit">Buscar</button>
</form>

<table>
    <thead>
    <tr>
        <th>Comercial</th>
        <th>Número de Clientes</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${resumen}" var="entry">
        <tr>
            <td>${entry.key}</td>
            <td>${entry.value}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<form method="get" action="${pageContext.request.contextPath}/pedidos/search">
    <label for="min">Cantidad mínima:</label>
    <input type="number" step="0.01" name="min" id="min" required>
    <label for="max">Cantidad máxima:</label>
    <input type="number" step="0.01" name="max" id="max" required>
    <button type="submit">Buscar</button>
</form>


