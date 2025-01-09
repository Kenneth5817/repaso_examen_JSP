package org.example.examen_repaso.dao;

import org.example.examen_repaso.model.Cliente;
import org.example.examen_repaso.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAOImpl extends AbstractDAOImpl implements ClienteDAO {

    @Override
    public void create(Cliente cliente) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();

            // Preparamos la sentencia SQL para insertar un nuevo cliente
            ps = conn.prepareStatement("INSERT INTO cliente (nombre, apellido1, apellido2, ciudad, categoria) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setString(idx++, cliente.getNombre());
            ps.setString(idx++, cliente.getApellido1());
            ps.setString(idx++, cliente.getApellido2());
            ps.setString(idx++, cliente.getCiudad());
            ps.setString(idx++, cliente.getCategoria());

            // Ejecutamos la inserción
            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de cliente con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                // Obtenemos el ID generado
                cliente.setId(rsGenKeys.getInt(1));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, null);
        }
    }

    @Override
    public List<Cliente> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Cliente> listCliente = new ArrayList<>();

        try {
            conn = connectDB();

            // Se utiliza Statement porque no hay parámetros en la consulta
            s = conn.createStatement();

            rs = s.executeQuery("SELECT * FROM cliente");
            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido1(rs.getString("apellido1"));
                cliente.setApellido2(rs.getString("apellido2"));
                cliente.setCiudad(rs.getString("ciudad"));
                cliente.setCategoria(rs.getString("categoria"));

                listCliente.add(cliente);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listCliente;
    }

    @Override
    public Optional<Cliente> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            // Preparamos la consulta para encontrar un cliente por su ID
            ps = conn.prepareStatement("SELECT * FROM cliente WHERE id = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido1(rs.getString("apellido1"));
                cliente.setApellido2(rs.getString("apellido2"));
                cliente.setCiudad(rs.getString("ciudad"));
                cliente.setCategoria(rs.getString("categoria"));

                return Optional.of(cliente);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

        return Optional.empty();
    }

    @Override
    public void update(Cliente cliente) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectDB();

            // Verificamos si el cliente es nulo antes de actualizar
            if (cliente == null) {
                System.out.println("Cliente es null, no se puede actualizar.");
                return;
            }

            // Preparamos la sentencia SQL para actualizar un cliente
            ps = conn.prepareStatement("UPDATE cliente SET nombre = ?, apellido1 = ?, apellido2 = ?, ciudad = ?, categoria = ? WHERE id = ?");

            int idx = 1;
            ps.setString(idx++, cliente.getNombre());
            ps.setString(idx++, cliente.getApellido1());
            ps.setString(idx++, cliente.getApellido2());
            ps.setString(idx++, cliente.getCiudad());
            ps.setString(idx++, cliente.getCategoria());
            ps.setInt(idx++, cliente.getId());

            // Ejecutamos la actualización
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Cliente actualizado correctamente.");
            } else {
                System.out.println("No se encontró el cliente o no se actualizó.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, null);
        }
    }

    @Override
    public void delete(int id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectDB();

            // Preparamos la sentencia SQL para eliminar un cliente por su ID
            ps = conn.prepareStatement("DELETE FROM cliente WHERE id = ?");
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de cliente con 0 registros eliminados.");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, null);
        }
    }

        // Método para guardar un cliente en la base de datos
        public void save(Cliente cliente) {
            String query = """
            INSERT INTO cliente (nombre, apellido1, apellido2, ciudad, categoria)
            VALUES (?, ?, ?, ?, ?)
        """;

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, cliente.getNombre());
                statement.setString(2, cliente.getApellido1());
                statement.setString(3, cliente.getApellido2());
                statement.setString(4, cliente.getCiudad());
                statement.setString(5, cliente.getCategoria());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                // Aquí podrías lanzar una excepción personalizada si es necesario
            }
        }
}
