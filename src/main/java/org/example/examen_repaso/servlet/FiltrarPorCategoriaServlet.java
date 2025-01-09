package org.example.examen_repaso.servlet;

import org.example.examen_repaso.dao.PedidoDAOImpl;
import org.example.examen_repaso.model.Pedido;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.examen_repaso.dao.PedidoDAO;
import org.example.examen_repaso.model.Pedido;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "FiltrarPorCategoriaServlet", value = "FiltrarPorCategoriaServlet")
public class FiltrarPorCategoriaServlet extends HttpServlet {

    private PedidoDAO pedidoDAO = new PedidoDAO() {
        @Override
        public List<Pedido> getPedidos() {
            return List.of();
        }

        @Override
        public List<Pedido> getPedidosConDetalle() throws SQLException {
            return List.of();
        }

        @Override
        public void savePedido(Pedido pedido) throws SQLException {

        }

        @Override
        public Map<String, Integer> getResumenClientesPorComercial() throws SQLException {
            return Map.of();
        }

        @Override
        public List<Pedido> getPedidosByCantidad(double min, double max) throws SQLException {
            return List.of();
        }

        @Override
        public List<Pedido> getAll() throws SQLException {
            return List.of();
        }

        @Override
        public Optional<Pedido> find(int id) throws SQLException {
            return Optional.empty();
        }

        @Override
        public void update(Pedido pedido) throws SQLException {

        }

        @Override
        public void delete(int id) throws SQLException {

        }

        @Override
        public PedidoDAOImpl create(Pedido pedido) throws SQLException {
            return null;
        }

        @Override
        public List<Pedido> buscarPedidosPorRango(double cantidadMin, double cantidadMax) {
            return List.of();
        }
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los valores de las categorías mínima y máxima del formulario
        int categoriaMin = Integer.parseInt(request.getParameter("categoriaMin"));
        int categoriaMax = Integer.parseInt(request.getParameter("categoriaMax"));

        // Filtrar los pedidos por el rango de categorías de los clientes
        List<Pedido> pedidosFiltrados = PedidoDAOImpl.filterByCategory(categoriaMin, categoriaMax);

        // Pasar los pedidos filtrados a la vista (JSP)
        request.setAttribute("pedidos", pedidosFiltrados);

        // Enviar la solicitud al JSP para mostrar los resultados
        request.getRequestDispatcher("/WEB-INF/jsp/listarPedidos.jsp").forward(request, response);
    }
}

