package org.example.examen_repaso.dao;

import org.example.examen_repaso.model.Comercial;
import org.example.examen_repaso.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ComercialDAO {
    static List<Comercial> getAll() {
            // Implementación estática directa
            return new ArrayList<>(); // Retorna una lista vacía como ejemplo

    }


    public List<Comercial> getAllComerciales() throws SQLException;

    public Comercial getComercialById(int id) throws SQLException;

    public void saveComercial(Comercial comercial) throws SQLException;

    public void updateComercial(Comercial comercial) throws SQLException;

    public void deleteComercial(int id) throws SQLException;
}
