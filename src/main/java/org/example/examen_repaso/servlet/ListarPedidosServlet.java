package org.example.examen_repaso.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.examen_repaso.dao.*;
import org.example.examen_repaso.model.Cliente;
import org.example.examen_repaso.model.Comercial;
import org.example.examen_repaso.model.Pedido;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "ListarPedidosServlet", value = "/ListarPedidosServlet")
public class ListarPedidosServlet extends HttpServlet {
    private ClienteDAOImpl clienteDAO = new ClienteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listarPedidos.jsp");

        List<Cliente> listado = this.clienteDAO.getAll();
        request.setAttribute("listado", listado);

        dispatcher.forward(request, response);

    }
}
