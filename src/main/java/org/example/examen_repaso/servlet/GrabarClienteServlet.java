package org.example.examen_repaso.servlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.examen_repaso.dao.ClienteDAOImpl;
import org.example.examen_repaso.model.Cliente;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "GrabarClienteServlet", value = "/GrabarClienteServlet")
public class GrabarClienteServlet extends HttpServlet {

    // EL SERVLET TIENE INSTANCIADO EL DAO PARA ACCESO A BBDD A LA TABLA CLIENTE
    private ClienteDAOImpl clienteDAO = new ClienteDAOImpl();

    // MÉTODO PARA RUTAS GET /GrabarClienteServlet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // REDIRECCIÓN A FORMULARIO PARA GRABAR CLIENTE
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioCliente.jsp");
        dispatcher.forward(request, response); // Redirección interna a la JSP de formulario
    }

    // MÉTODO PARA RUTAS POST /GrabarClienteServlet
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = null;

        // VALIDACIÓN ENCAPSULADA EN UN MÉTODO DE UTILERÍA
        Optional<Cliente> optionalCliente = UtilServlet.validaGrabar(request);

        // SI EL OPTIONAL CON CLIENTE ESTÁ PRESENTE <--> VALIDACIÓN CORRECTA
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();

            // PERSISTO EL CLIENTE NUEVO EN BBDD
            clienteDAO.save(cliente);

            // CARGO TODO EL LISTADO DE CLIENTES DESDE BBDD
            List<Cliente> listado = clienteDAO.getAll();

            // PASO EL LISTADO DE CLIENTES A LA JSP
            request.setAttribute("listado", listado);

            // PASO EL ID DEL NUEVO CLIENTE A LA JSP
            request.setAttribute("newClienteID", cliente.getId());

            // REDIRECCIÓN A LA VISTA DE LISTADO DE CLIENTES
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listadoClientes.jsp");
        } else {
            // EL OPTIONAL ESTÁ VACÍO, ERROR DE VALIDACIÓN
            request.setAttribute("error", "Error de validación!");

            // REDIRECCIÓN A FORMULARIO DE CLIENTE
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crearCliente.jsp");
        }

        // FINALIZAR LA REDIRECCIÓN INTERNA
        dispatcher.forward(request, response);
    }
}
