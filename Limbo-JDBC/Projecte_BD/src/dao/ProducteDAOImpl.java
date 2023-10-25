package dao;

import model.Producte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProducteDAOImpl implements ProducteDAO {
    private static String DB_USERNAME = "root";
    private static String DB_PASSWORD = "";
    private static String DB_HOST = "localhost:3307";
    private static String DB_NAME = "limbo";

    @Override
    public List<Producte> obtenerProductosSugeridos() {
        List<Producte> productosSugeridos = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            try (PreparedStatement statement = con.prepareStatement(
                    "SELECT * FROM producte ORDER BY RAND() LIMIT 5;"
            )) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Producte producte = new Producte(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("descripcio"),
                            rs.getDouble("pvp"),
                            rs.getInt("iva"),
                            rs.getString("marca"),
                            rs.getString("unitat_mesura"),
                            rs.getDouble("pes"),
                            rs.getInt("categoria")
                    );
                    productosSugeridos.add(producte);
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productosSugeridos;
    }

    @Override
    public List<Producte> filtrarPorNombre(String valorFiltro) {
        List<Producte> productosFiltrados = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            try (PreparedStatement statement = con.prepareStatement(
                    "SELECT * FROM producte WHERE nom LIKE ?;"
            )) {
                statement.setString(1, "%" + valorFiltro + "%");
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Producte producte = new Producte(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("descripcio"),
                            rs.getDouble("pvp"),
                            0,
                            rs.getString("marca"),
                            "",
                            0.0,
                            rs.getInt("categoria")
                    );
                    productosFiltrados.add(producte);
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productosFiltrados;
    }

    @Override
    public List<Producte> filtrarPorDescripcion(String valorFiltro) {
        List<Producte> productosFiltrados = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            try (PreparedStatement statement = con.prepareStatement(
                    "SELECT * FROM producte WHERE descripcio LIKE ?;"
            )) {
                statement.setString(1, "%" + valorFiltro + "%");
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Producte producte = new Producte(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("descripcio"),
                            rs.getDouble("pvp"),
                            0,
                            rs.getString("marca"),
                            "",
                            0.0,
                            rs.getInt("categoria")
                    );
                    productosFiltrados.add(producte);
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productosFiltrados;
    }

    @Override
    public List<Producte> filtrarPorMarca(String valorFiltro) {
        List<Producte> productosFiltrados = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            try (PreparedStatement statement = con.prepareStatement(
                    "SELECT * FROM producte WHERE marca LIKE ?;"
            )) {
                statement.setString(1, "%" + valorFiltro + "%");
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Producte producte = new Producte(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("descripcio"),
                            rs.getDouble("pvp"),
                            0,
                            rs.getString("marca"),
                            "",
                            0.0,
                            rs.getInt("categoria")
                    );
                    productosFiltrados.add(producte);
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productosFiltrados;
    }

    @Override
    public List<Producte> filtrarPorCategoria(String categoriaId) {
        List<Producte> productosFiltrados = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            try (PreparedStatement statement = con.prepareStatement(
                    "SELECT * FROM producte WHERE categoria = ?;"
            )) {
                statement.setString(1, categoriaId);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Producte producte = new Producte(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("descripcio"),
                            rs.getDouble("pvp"),
                            0,
                            rs.getString("marca"),
                            "",
                            0.0,
                            rs.getInt("categoria")


                    );
                    productosFiltrados.add(producte);
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productosFiltrados;
    }

    @Override
    public List<Producte> mostrarTodosProductos() {
        List<Producte> productosFiltrados = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            try (PreparedStatement statement = con.prepareStatement(
                    "SELECT * FROM producte;"
            )) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Producte producte = new Producte(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("descripcio"),
                            rs.getDouble("pvp"),
                            0,
                            rs.getString("marca"),
                            "",
                            0.0,
                            rs.getInt("categoria")
                    );
                    productosFiltrados.add(producte);
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productosFiltrados;
    }

    @Override
    public Producte obtenerProductoPorId(int producteId) {
        Producte producto = null;
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            String query = "SELECT * FROM producte WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, producteId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                double pvp = resultSet.getDouble("pvp");

                producto = new Producte(id, nom, pvp);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }
}
