package org.example.examen_repaso.dao;

import org.example.examen_repaso.model.Comercial;
import org.example.examen_repaso.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComercialDAOImpl {

    public List<Comercial> getAllComerciales() throws SQLException {
        List<Comercial> comerciales = new ArrayList<>();
        String query = "SELECT id, nombre, apellido1, apellido2, comision FROM comercial";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Comercial comercial = new Comercial();
                comercial.setId(resultSet.getInt("id"));
                comercial.setNombre(resultSet.getString("nombre"));
                comercial.setApellido1(resultSet.getString("apellido1"));
                comercial.setApellido2(resultSet.getString("apellido2"));
                comercial.setComision((float) resultSet.getDouble("comision"));
                comerciales.add(comercial);
            }
        }
        return comerciales;
    }

    public Comercial getComercialById(int id) throws SQLException {
        String query = "SELECT id, nombre, apellido1, apellido2, comision FROM comercial WHERE id = ?";
        Comercial comercial = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    comercial = new Comercial();
                    comercial.setId(resultSet.getInt("id"));
                    comercial.setNombre(resultSet.getString("nombre"));
                    comercial.setApellido1(resultSet.getString("apellido1"));
                    comercial.setApellido2(resultSet.getString("apellido2"));
                    comercial.setComision((float) resultSet.getDouble("comision"));
                }
            }
        }
        return comercial;
    }

    public void saveComercial(Comercial comercial) throws SQLException {
        String query = "INSERT INTO comercial (nombre, apellido1, apellido2, comision) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, comercial.getNombre());
            statement.setString(2, comercial.getApellido1());
            statement.setString(3, comercial.getApellido2());
            statement.setDouble(4, comercial.getComision());
            statement.executeUpdate();
        }
    }

    public void updateComercial(Comercial comercial) throws SQLException {
        String query = "UPDATE comercial SET nombre = ?, apellido1 = ?, apellido2 = ?, comision = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, comercial.getNombre());
            statement.setString(2, comercial.getApellido1());
            statement.setString(3, comercial.getApellido2());
            statement.setDouble(4, comercial.getComision());
            statement.setInt(5, comercial.getId());
            statement.executeUpdate();
        }
    }

    public void deleteComercial(int id) throws SQLException {
        String query = "DELETE FROM comercial WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
