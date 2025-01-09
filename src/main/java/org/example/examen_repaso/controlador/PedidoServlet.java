package org.example.examen_repaso.controlador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.examen_repaso.dao.ClienteDAO;
import org.example.examen_repaso.dao.ComercialDAO;
import org.example.examen_repaso.dao.PedidoDAO;
import org.example.examen_repaso.modelo.Cliente;
import org.example.examen_repaso.modelo.Comercial;
import org.example.examen_repaso.modelo.Pedido;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/pedidos/*")
public class PedidoServlet extends HttpServlet {
    private PedidoDAO pedidoDao;
    private ClienteDAO clienteDao;
    private ComercialDAO comercialDao;

    @Override
    public void init() {
        pedidoDao = new PedidoDAO();
        clienteDao = new ClienteDAO();
        comercialDao = new ComercialDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) action = "/";

        try {
            switch (action) {
                case "/new":
                    showNewPedidoForm(request, response);
                    break;
                case "/list":
                    listPedidos(request, response);
                    break;
                case "/search":
                    searchPedidosByCantidad(request, response);
                    break;
                default:
                    listPedidos(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void showNewPedidoForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Cliente> clientes = ClienteDAO.getAllClientes();
        List<Comercial> comerciales = comercialDao.getAllComerciales();
        request.setAttribute("clientes", clientes);
        request.setAttribute("comerciales", comerciales);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/new-pedido.jsp");
        dispatcher.forward(request, response);
    }

    private void insertPedido(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        double total = Double.parseDouble(request.getParameter("total"));
        String fechaStr = request.getParameter("fecha");
        LocalDate fecha = LocalDate.parse(fechaStr);
        int clienteId = Integer.parseInt(request.getParameter("clienteId"));
        int comercialId = Integer.parseInt(request.getParameter("comercialId"));

        if (total < 0) {
            request.setAttribute("error", "El total no puede ser negativo.");
            showNewPedidoForm(request, response);
            return;
        }

        if (fecha.isBefore(LocalDate.now())) {
            request.setAttribute("error", "La fecha no puede ser anterior al día actual.");
            showNewPedidoForm(request, response);
            return;
        }

        Pedido pedido = new Pedido(total, fecha, clienteId, comercialId);
        pedidoDao.savePedido(pedido);
        response.sendRedirect("list");
    }

    private void listPedidos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Pedido> pedidos = pedidoDao.getPedidosConDetalle();
        Map<String, Integer> resumen = pedidoDao.getResumenClientesPorComercial();

        request.setAttribute("pedidos", pedidos);
        request.setAttribute("resumen", resumen);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listarPedido.jsp");
        dispatcher.forward(request, response);
    }

    private void searchPedidosByCantidad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        double min = Double.parseDouble(request.getParameter("min"));
        double max = Double.parseDouble(request.getParameter("max"));

        List<Pedido> pedidos = pedidoDao.getPedidosByCantidad(min, max);
        request.setAttribute("pedidos", pedidos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listarPedido.jsp");
        dispatcher.forward(request, response);
    }
}
