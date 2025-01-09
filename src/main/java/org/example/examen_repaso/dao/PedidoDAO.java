package org.example.examen_repaso.dao;
import org.example.examen_repaso.modelo.Pedido;
import org.example.examen_repaso.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoDAO {
    public List<Pedido> getPedidosConDetalle() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String query = """
            SELECT p.id, p.total, p.fecha, p.id_cliente, p.id_comercial
            FROM pedido p
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(resultSet.getInt("id"));
                pedido.setTotal(resultSet.getDouble("total"));
                pedido.setFecha(resultSet.getDate("fecha").toLocalDate());
                pedido.setIdCliente(resultSet.getInt("id_cliente"));
                pedido.setIdComercial(resultSet.getInt("id_comercial"));
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    public void savePedido(Pedido pedido) throws SQLException {
        String query = """
            INSERT INTO pedido (total, fecha, id_cliente, id_comercial)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, pedido.getTotal());
            statement.setDate(2, Date.valueOf(pedido.getFecha()));
            statement.setInt(3, pedido.getIdCliente());
            statement.setInt(4, pedido.getIdComercial());
            statement.executeUpdate();
        }
    }

    public Map<String, Integer> getResumenClientesPorComercial() throws SQLException {
        String query = "SELECT CONCAT(comercial.nombre, ' ', comercial.apellido1, ' ', comercial.apellido2) AS comercial, " +
                "COUNT(DISTINCT cliente.id) AS num_clientes " +
                "FROM pedido " +
                "JOIN comercial ON pedido.id_comercial = comercial.id " +
                "JOIN cliente ON pedido.id_cliente = cliente.id " +
                "GROUP BY comercial";
        Map<String, Integer> resumen = new HashMap<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String comercial = resultSet.getString("comercial");
                int numClientes = resultSet.getInt("num_clientes");
                resumen.put(comercial, numClientes);
            }
        }
        return resumen;
    }
    public List<Pedido> getPedidosByCantidad(double min, double max) throws SQLException {
        String query = "SELECT * FROM pedido WHERE total BETWEEN ? AND ?";
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, min);
            statement.setDouble(2, max);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(resultSet.getInt("id"));
                    pedido.setTotal(resultSet.getDouble("total"));
                    pedido.setFecha(resultSet.getDate("fecha").toLocalDate());
                    pedido.setIdCliente(resultSet.getInt("id_cliente"));
                    pedido.setIdComercial(resultSet.getInt("id_comercial"));
                    pedidos.add(pedido);
                }
            }
        }
        return pedidos;
    }


}
