package org.example.examen_repaso.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.examen_repaso.dao.ClienteDAO;
import org.example.examen_repaso.dao.ComercialDAO;
import org.example.examen_repaso.dao.PedidoDAO;
import org.example.examen_repaso.dao.PedidoDAOImpl;
import org.example.examen_repaso.model.Cliente;
import org.example.examen_repaso.model.Comercial;
import org.example.examen_repaso.model.Pedido;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "CrearPedidosServlet", value = "/CrearPedidosServlet")
public class CrearPedidosServlet extends HttpServlet {

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
    private ClienteDAO clienteDAO = new ClienteDAO() {
        @Override
        public void create(Cliente cliente) {

        }

        @Override
        public List<Cliente> getAll() {
            return List.of();
        }

        @Override
        public Optional<Cliente> find(int id) {
            return Optional.empty();
        }

        @Override
        public void update(Cliente socio) {

        }

        @Override
        public void delete(int id) {

        }

        @Override
        public void save(Cliente cliente) {

        }
    };
    private ComercialDAO comercialDAO = new ComercialDAO() {
        @Override
        public List<Comercial> getAllComerciales() throws SQLException {
            return List.of();
        }

        @Override
        public Comercial getComercialById(int id) throws SQLException {
            return null;
        }

        @Override
        public void saveComercial(Comercial comercial) throws SQLException {

        }

        @Override
        public void updateComercial(Comercial comercial) throws SQLException {

        }

        @Override
        public void deleteComercial(int id) throws SQLException {

        }
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Cargar clientes y comerciales para los combos de selección
        List<Cliente> clientes = clienteDAO.getAll();
        List<Comercial> comerciales = ComercialDAO.getAll();

        // Pasar los datos a la vista (JSP)
        request.setAttribute("clientes", clientes);
        request.setAttribute("comerciales", comerciales);

        // Enviar la solicitud al JSP para mostrar el formulario de crear pedido
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crearPedido.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        // Obtener los datos del formulario
        double total = Double.parseDouble(request.getParameter("total"));
        String fechaStr = request.getParameter("fecha");
        int clienteId = Integer.parseInt(request.getParameter("cliente"));
        int comercialId = Integer.parseInt(request.getParameter("comercial"));

        // Validación de datos
        if (total < 0) {
            request.setAttribute("error", "La cantidad del pedido no puede ser negativa.");
            doGet(request, response);
            return;
        }

        LocalDate fecha = LocalDate.parse(fechaStr);
        if (fecha.isBefore(LocalDate.now())) {
            request.setAttribute("error", "La fecha no puede ser anterior al día actual.");
            doGet(request, response);
            return;
        }

        // Crear el nuevo pedido
        Pedido nuevoPedido = new Pedido(total, fecha, clienteId, comercialId);
        try {
            PedidoDAOImpl pedidoDAO1 = pedidoDAO.create(nuevoPedido);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Redirigir al listado de pedidos
        response.sendRedirect("ListarPedidosServlet");
    }
}
