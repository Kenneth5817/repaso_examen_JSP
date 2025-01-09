package org.example.examen_repaso.servlet;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.examen_repaso.dao.ClienteDAOImpl;
import org.example.examen_repaso.model.Cliente;


import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarClientesServlet", value = "/ListarClientesServlet")
public class ListarClientesServlet extends HttpServlet {

    private ClienteDAOImpl clienteDAO = new ClienteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listadoSociosB.jsp");

        List<Cliente> listado = this.clienteDAO.getAll();
        request.setAttribute("listado", listado);

        dispatcher.forward(request, response);

    }

}
