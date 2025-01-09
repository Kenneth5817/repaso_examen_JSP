<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 09/01/2025
  Time: 21:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Prueba</title>
</head>
<body>

<!--TYPE RADIOBUTTON-->
<form action="FiltrarPorCategoriaServlet" method="POST">
    <label>
        <input type="radio" name="categoria" value="tecnologia"> Tecnología
    </label>
    <label>
        <input type="radio" name="categoria" value="moda"> Moda
    </label>
    <label>
        <input type="radio" name="categoria" value="hogar"> Hogar
    </label>
    <button type="submit">Enviar</button>
</form>

<!-- EN SERVLET-->
<!--String categoria = request.getParameter("categoria");
if (categoria != null) {
out.println("Categoría seleccionada: " + categoria);
} else {
out.println("No se seleccionó ninguna categoría.");
} -->


<!--OPTIONAL INPUT CON REQUIRED O SIN EL-->
<form action="ProcesarDatosServlet" method="POST">
    <label for="nombre">Nombre (opcional):</label>
    <input type="text" id="nombre" name="nombre">

    <label for="email">Email (obligatorio):</label>
    <input type="email" id="email" name="email" required>

    <button type="submit">Enviar</button>
</form>

<!-- EN SERVLET
String nombre = request.getParameter("nombre"); // Puede ser null si no se completa
String email = request.getParameter("email"); // No puede ser null porque es obligatorio

if (email == null || email.isEmpty()) {
    out.println("El campo email es obligatorio.");
} else {
    out.println("Datos recibidos: Nombre - " + (nombre != null ? nombre : "No ingresado") + ", Email - " + email);
}
-->

<!--PROCESADOR DE NULOS-->
<form action="ProcesarNulosServlet" method="POST">
    <label for="edad">Edad (opcional):</label>
    <input type="number" id="edad" name="edad">
    <button type="submit">Enviar</button>
</form>

<!--SERVLET

String edad = request.getParameter("edad");
if (edad == null || edad.isEmpty()) {
    out.println("Edad no proporcionada.");
} else {
    out.println("Edad proporcionada: " + edad);
}
-->

<!--MULTISELECT (multiseleccion)
String[] productos = request.getParameterValues("productos");
if (productos != null) {
    out.println("Productos seleccionados:");
    for (String producto : productos) {
        out.println("- " + producto);
    }
} else {
    out.println("No se seleccionaron productos.");
}
-->

<!-- GET PARAMETER -->
<form action="TestServlet" method="GET">
    <input type="text" name="usuario">
    <button type="submit">Enviar</button>
</form>

<!--SERVLET
String usuario = request.getParameter("usuario");
if (usuario == null) {
    out.println("No se envió el parámetro 'usuario'.");
} else {
    out.println("Usuario recibido: " + usuario);
}
-->

<!--OPTGROUP: Para agrupar opciones en un desplegable -->
<form action="OptGroupServlet" method="POST">
    <label for="categorias">Selecciona una categoría:</label>
    <select name="categorias" id="categorias">
        <optgroup label="Tecnología">
            <option value="smartphones">Smartphones</option>
            <option value="ordenadores">Ordenadores</option>
        </optgroup>
        <optgroup label="Hogar">
            <option value="muebles">Muebles</option>
            <option value="electrodomesticos">Electrodomésticos</option>
        </optgroup>
    </select>
    <button type="submit">Enviar</button>
</form>
<!--SERVLET String categoria = request.getParameter("categorias");
if (categoria != null) {
    out.println("Categoría seleccionada: " + categoria);
} else {
    out.println("No se seleccionó ninguna categoría.");
}
-->
</body>
</html>
