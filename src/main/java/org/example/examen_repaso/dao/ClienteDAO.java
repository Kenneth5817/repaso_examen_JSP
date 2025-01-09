package org.example.examen_repaso.dao;

import org.example.examen_repaso.modelo.Cliente;
import org.example.examen_repaso.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public Cliente getClienteById(int id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido1(rs.getString("apellido1"));
                    cliente.setApellido2(rs.getString("apellido2"));
                    cliente.setCiudad(rs.getString("ciudad"));
                    cliente.setCategoria(rs.getInt("categoria"));
                    return cliente;
                }
            }
        }
        return null;
    }

    public void updateCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nombre = ?, apellido1 = ?, apellido2 = ?, ciudad = ?, categoria = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido1());
            stmt.setString(3, cliente.getApellido2());
            stmt.setString(4, cliente.getCiudad());
            stmt.setInt(5, cliente.getCategoria());
            stmt.setInt(6, cliente.getId());
            stmt.executeUpdate();
        }
    }
    // Método para obtener todos los clientes
    public static List<Cliente> getAllClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM cliente";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas", "root", "root");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                String ciudad = rs.getString("ciudad");
                int categoria = rs.getInt("categoria");

                // Crear un objeto Cliente y añadirlo a la lista
                clientes.add(new Cliente(id, nombre, apellido1, apellido2, ciudad, categoria));
            }
        }
        return clientes;
    }

}
