package org.example.examen_repaso.controlador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.examen_repaso.dao.ClienteDAO;
import org.example.examen_repaso.modelo.Cliente;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.SQLException;

    @WebServlet("/clientes/*")
    public class ClienteServlet extends HttpServlet {
        private ClienteDAO clienteDao;

        @Override
        public void init() {
            clienteDao = new ClienteDAO();
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String action = request.getPathInfo();
            if (action == null) action = "/";

            switch (action) {
                case "/edit":
                    showEditClienteForm(request, response);
                    break;
                case "/update":
                    updateCliente(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        }

        private void showEditClienteForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                Cliente cliente = clienteDao.getClienteById(id);
                request.setAttribute("cliente", cliente);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/edit-cliente.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }

        private void updateCliente(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                String apellido1 = request.getParameter("apellido1");
                String apellido2 = request.getParameter("apellido2");
                String ciudad = request.getParameter("ciudad");
                int categoria = Integer.parseInt(request.getParameter("categoria"));

                if (nombre == null || nombre.isEmpty() || apellido1 == null || apellido1.isEmpty()) {
                    request.setAttribute("error", "Nombre y Apellido1 son obligatorios.");
                    showEditClienteForm(request, response);
                    return;
                }

                if (categoria < 1 || categoria > 10) {
                    request.setAttribute("error", "La categoría debe estar entre 1 y 10.");
                    showEditClienteForm(request, response);
                    return;
                }

                Cliente cliente = new Cliente();
                cliente.setId(id);
                cliente.setNombre(nombre);
                cliente.setApellido1(apellido1);
                cliente.setApellido2(apellido2);
                cliente.setCiudad(ciudad);
                cliente.setCategoria(categoria);

                clienteDao.updateCliente(cliente);
                response.sendRedirect("/pedidos/list");
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
    }
