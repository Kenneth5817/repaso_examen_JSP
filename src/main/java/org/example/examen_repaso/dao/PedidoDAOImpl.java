package org.example.examen_repaso.dao;

import org.example.examen_repaso.model.Cliente;
import org.example.examen_repaso.model.Comercial;
import org.example.examen_repaso.model.Pedido;
import org.example.examen_repaso.util.DatabaseConnection;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PedidoDAOImpl implements PedidoDAO {

    @Override
    public List<Pedido> getPedidos() {
        // Implementación del método
        return new ArrayList<>();
    }

    // Método para filtrar pedidos por el rango de categorías de los clientes
    public static List<Pedido> filterByCategory(int categoriaMin, int categoriaMax) {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT p.id, p.total, p.fecha, p.id_cliente, p.id_comercial " +
                "FROM pedido p " +
                "JOIN cliente c ON p.id_cliente = c.id " +
                "WHERE c.categoria BETWEEN ? AND ?";

        // Utilizar DatabaseConnection.getConnection() en lugar de Connection.prepareStatement()
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, categoriaMin);
            stmt.setInt(2, categoriaMax);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pedido pedido = new Pedido(
                            rs.getInt("id"),
                            rs.getDouble("total"),
                            rs.getDate("fecha").toLocalDate(),
                            rs.getInt("id_cliente"),
                            rs.getInt("id_comercial")
                    );
                    pedidos.add(pedido);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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
    @Override
    public List<Pedido> getAll() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT * FROM pedido";

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

    // Método para buscar un pedido por su ID
    @Override
    public Optional<Pedido> find(int id) throws SQLException {
        String query = "SELECT * FROM pedido WHERE id = ?";
        Pedido pedido = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
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
        }

        return Optional.ofNullable(pedido);
    }
    @Override
    public void update(Pedido pedido) throws SQLException {
        String query = "UPDATE pedido SET total = ?, fecha = ?, id_cliente = ?, id_comercial = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, pedido.getTotal());
            statement.setDate(2, Date.valueOf(pedido.getFecha()));
            statement.setInt(3, pedido.getIdCliente());
            statement.setInt(4, pedido.getIdComercial());
            statement.setInt(5, pedido.getId());

            statement.executeUpdate();
        }
    }

    // Método para eliminar un pedido
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM pedido WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public PedidoDAOImpl create(Pedido pedido) throws SQLException {
        String query = "INSERT INTO pedido (id, total, fecha, id_cliente, id_comercial) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Establecer los valores del pedido en la consulta
            statement.setInt(1, pedido.getId());
            statement.setDouble(2, pedido.getTotal());
            statement.setDate(3, Date.valueOf(pedido.getFecha())); // Convertimos LocalDate a java.sql.Date
            statement.setInt(4, pedido.getIdCliente());
            statement.setInt(5, pedido.getIdComercial());

            // Ejecutar la consulta
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-lanzar la excepción para manejarla en niveles superiores si es necesario
        }
        return null;
    }

    @Override
    public List<Pedido> buscarPedidosPorRango(double cantidadMin, double cantidadMax) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE total BETWEEN ? AND ?";  // SQL para buscar pedidos en el rango

        DatabaseConnection dataSource = null;
        try (Connection conn = dataSource.getConnection();  // Obtén la conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(sql)) {  // Preparamos la consulta

            // Establecemos los parámetros de la consulta (rango de cantidad)
            stmt.setDouble(1, cantidadMin);
            stmt.setDouble(2, cantidadMax);

            try (ResultSet rs = stmt.executeQuery()) {  // Ejecutamos la consulta y obtenemos los resultados
                while (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(rs.getInt("id"));  // Asignamos los valores de las columnas a los atributos del objeto Pedido
                    pedido.setTotal(rs.getDouble("total"));
                    pedido.setFecha(rs.getDate("fecha").toLocalDate());

                    // Suponiendo que tienes objetos Cliente y Comercial que se asignan con sus IDs
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id_cliente"));
                    pedido.setCliente(cliente);  // Asumiendo que el cliente ya existe o se puede cargar de forma similar

                    Comercial comercial = new Comercial();
                    comercial.setId(rs.getInt("id_comercial"));
                    pedido.setIdComercial(comercial.getId());  // Asumiendo que el comercial ya existe o se puede cargar

                    pedidos.add(pedido);  // Añadimos el pedido a la lista
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Manejo de la excepción de SQL
            // Aquí podrías lanzar una excepción personalizada o retornar un resultado vacío si prefieres
        }

        return pedidos;  // Retornamos la lista de pedidos encontrados
    }

}