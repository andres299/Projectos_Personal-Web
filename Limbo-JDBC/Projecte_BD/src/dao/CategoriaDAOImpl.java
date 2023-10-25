package dao;

import model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {
    private static String DB_USERNAME = "root";
    private static String DB_PASSWORD = "";
    private static String DB_HOST = "localhost:3307";
    private static String DB_NAME = "limbo";

    @Override
    public List<Categoria> mostrarTodasCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            try (Statement statement = con.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT id, nom, descripcio FROM categoria;");
                while (rs.next()) {
                    Categoria categoria = new Categoria(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("descripcio")
                    );
                    categorias.add(categoria);
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categorias;
    }
}
