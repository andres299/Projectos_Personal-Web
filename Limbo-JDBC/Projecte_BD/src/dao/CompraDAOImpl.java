package dao;

import model.Compra;

import java.sql.*;

public class CompraDAOImpl implements CompraDAO {
    private static String DB_USERNAME = "root";
    private static String DB_PASSWORD = "";
    private static String DB_HOST = "localhost:3307";
    private static String DB_NAME = "limbo";

    @Override
    public int guardarCompra(Compra compra) {
        int id = 0;
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            // Preparar la consulta SQL para insertar la compra en la tabla correspondiente
            String sql = "INSERT INTO compra (id_transaccio, data) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, compra.getId_transaccio());
            stmt.setDate(2, compra.getData());
            stmt.executeUpdate();

            try (ResultSet resultadodelaQuery = stmt.getGeneratedKeys()) {
                if (resultadodelaQuery.next()) {
                    id = resultadodelaQuery.getInt(1);
                }
            }

            stmt.close();
            con.close();

            System.out.println("Compra guardada con éxito en la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al guardar la compra en la base de datos: " + e.getMessage());
        }
        return id;
    }
}