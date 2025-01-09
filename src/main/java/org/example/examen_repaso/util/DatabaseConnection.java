package org.example.examen_repaso.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
                Properties prop = new Properties();
                prop.load(input);
                String url = prop.getProperty("db.url");
                String user = prop.getProperty("db.user");
                String password = prop.getProperty("db.password");
                connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                throw new SQLException("Error al conectar con la base de datos.", e);
            }
        }
        return connection;
    }
}
