<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 09/01/2025
  Time: 0:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nuevo Pedido</title>
</head>
<body>
<h1>Nuevo Pedido</h1>
<form method="post" action="/pedidos/insert">
    <label for="total">Total:</label>
    <input type="number" step="0.01" name="total" id="total" required><br>

    <label for="fecha">Fecha:</label>
    <input type="date" name="fecha" id="fecha" required><br>

    <label for="clienteId">Cliente:</label>
    <select name="clienteId" id="clienteId">
        <c:forEach items="${clientes}" var="cliente">
            <option value="${cliente.id}">${cliente.nombre} ${cliente.apellido1}</option>
        </c:forEach>
    </select><br>

    <label for="comercialId">Comercial:</label>
    <select name="comercialId" id="comercialId">
        <c:forEach items="${comerciales}" var="comercial">
            <option value="${comercial.id}">${comercial.nombre} ${comercial.apellido1}</option>
        </c:forEach>
    </select><br>

    <button type="submit">Crear Pedido</button>
</form>
<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>
</body>
</html>
