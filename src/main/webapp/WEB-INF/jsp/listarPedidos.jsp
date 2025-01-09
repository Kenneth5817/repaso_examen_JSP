
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

<!--Tabla pedidos con todos los datos arriba campos abajo valores-->
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
                <a href="/pedidos/editar?id=${pedido.id}">Borrar</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!--Cantidad minima, maxima y buscar -->
<form action="/pedidos/buscar" method="get">
    <input type="number" name="min" placeholder="Cantidad mínima">
    <input type="number" name="max" placeholder="Cantidad máxima">
    <button type="submit">Buscar</button>
</form>

<!--Tabla clave valor arriba campos, abajo valores-->
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


