package org.example.examen_repaso.servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.examen_repaso.dao.ClienteDAO;
import org.example.examen_repaso.dao.ClienteDAOImpl;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "BorrarClienteServlet", value = "/BorrarClienteServlet")
public class BorrarClienteServlet extends HttpServlet {

    private ClienteDAO clienteDAO = new ClienteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID del cliente a borrar
        String idStr = request.getParameter("id");

        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);

                // Llamar al DAO para eliminar el cliente
                clienteDAO.delete(id);

                // Redirigir al listado de clientes o mostrar un mensaje de éxito
                response.sendRedirect("ListadoClientesServlet");

            } catch (NumberFormatException e) {
                // Manejo de errores (ejemplo simple)
                request.setAttribute("error", "No se pudo eliminar el cliente.");
                request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            }
        } else {
            // Si no se recibe un ID válido, redirigir o mostrar un mensaje
            response.sendRedirect("ListadoClientesServlet");
        }
    }
}
