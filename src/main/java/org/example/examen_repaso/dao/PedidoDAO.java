package org.example.examen_repaso.dao;

import org.example.examen_repaso.model.Pedido;
import org.example.examen_repaso.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

    public interface PedidoDAO {
            static Pedido getById(int pedidoId) throws SQLException {
                Pedido pedido = null;

                String query = "SELECT * FROM pedido WHERE id = ?";

                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement statement = connection.prepareStatement(query)) {

                    statement.setInt(1, pedidoId);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            pedido = new Pedido();
                            pedido.setId(resultSet.getInt("id"));
                            pedido.setTotal(resultSet.getDouble("total"));
                            pedido.setFecha(resultSet.getDate("fecha").toLocalDate());
                            pedido.setIdCliente(resultSet.getInt("id_cliente"));
                            pedido.setIdComercial(resultSet.getInt("id_comercial"));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw e; // Re-lanzar la excepción para manejarla a nivel superior
                }

                return pedido;
            }


        List<Pedido> getPedidos();

        // Método para obtener los pedidos con su detalle
        public List<Pedido> getPedidosConDetalle() throws SQLException;

        // Método para guardar un nuevo pedido
        public void savePedido(Pedido pedido) throws SQLException;

        // Método para obtener un resumen de clientes por comercial
        public Map<String, Integer> getResumenClientesPorComercial() throws SQLException;

        // Método para obtener pedidos filtrados por cantidad
        public List<Pedido> getPedidosByCantidad(double min, double max) throws SQLException;

        // Método para obtener todos los pedidos (sin detalle específico)
        List<Pedido> getAll() throws SQLException;
        // Método para buscar un pedido por su ID
        public Optional<Pedido> find(int id) throws SQLException;

        // Método para actualizar un pedido
        void update(Pedido pedido) throws SQLException;

        // Método para eliminar un pedido
        public void delete(int id) throws SQLException;

        public PedidoDAOImpl create(Pedido pedido) throws SQLException;

        public List<Pedido> buscarPedidosPorRango(double cantidadMin, double cantidadMax);
    }