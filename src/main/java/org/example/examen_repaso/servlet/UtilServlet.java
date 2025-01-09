package org.example.examen_repaso.servlet;

import jakarta.servlet.http.HttpServletRequest;
import org.example.examen_repaso.model.Cliente;

import java.util.Objects;
import java.util.Optional;

public class UtilServlet {

    public static Optional<Cliente> validaGrabar(HttpServletRequest request) {
        // CÓDIGO DE VALIDACIÓN
        boolean valida = true;
        String nombre = null;
        String apellido1 = null;
        String apellido2 = null;
        String ciudad = null;
        String categoria = null;

        try {
            // Validación de los parámetros
            Objects.requireNonNull(request.getParameter("nombre"));
            if (request.getParameter("nombre").isBlank()) throw new RuntimeException("Parámetro vacío o todo espacios blancos.");
            nombre = request.getParameter("nombre");

            // Validación de apellido1
            Objects.requireNonNull(request.getParameter("apellido1"));
            if (request.getParameter("apellido1").isBlank()) throw new RuntimeException("Parámetro vacío o todo espacios blancos.");
            apellido1 = request.getParameter("apellido1");

            // Validación de apellido2
            Objects.requireNonNull(request.getParameter("apellido2"));
            if (request.getParameter("apellido2").isBlank()) throw new RuntimeException("Parámetro vacío o todo espacios blancos.");
            apellido2 = request.getParameter("apellido2");

            // Validación de ciudad
            Objects.requireNonNull(request.getParameter("ciudad"));
            if (request.getParameter("ciudad").isBlank()) throw new RuntimeException("Parámetro vacío o todo espacios blancos.");
            ciudad = request.getParameter("ciudad");

            // Validación de categoria
            Objects.requireNonNull(request.getParameter("categoria"));
            if (request.getParameter("categoria").isBlank()) throw new RuntimeException("Parámetro vacío o todo espacios blancos.");
            categoria = request.getParameter("categoria");

            // Creación del objeto Cliente con los valores validados
            return Optional.of(new Cliente(-1, nombre, apellido1, apellido2, ciudad, categoria));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // En caso de error, devolver un Optional vacío
        return Optional.empty();
    }

}

