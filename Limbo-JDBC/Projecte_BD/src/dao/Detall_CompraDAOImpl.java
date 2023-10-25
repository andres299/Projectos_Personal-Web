package dao;

import model.Categoria;
import model.Detall_Compra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Detall_CompraDAOImpl implements Detall_CompraDAO {
    private static final String DB_USERNAME = "root";
    private static String DB_PASSWORD = "";
    private static String DB_HOST = "localhost:3307";
    private static String DB_NAME = "limbo";


    @Override
    public void guardarDetall_Compra(Detall_Compra detallCompra) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            String sql = "INSERT INTO detall_compra (compra_id, producte_id, pvp, unitats_producte) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, detallCompra.getCompra_id());
            stmt.setInt(2, detallCompra.getProducte_id());
            stmt.setDouble(3, detallCompra.getPvp());
            stmt.setInt(4, detallCompra.getUnitats_producte());

            stmt.executeUpdate();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar la compra en la base de datos: " + e.getMessage());
        }
    }

    @Override
    public String totalPVP() {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            String query = "SELECT SUM(pvp * unitats_producte) FROM detall_compra";
            try (Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    double totalPVP = resultSet.getDouble(1);
                    return String.format("%.2f", totalPVP);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.close();
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Detall_Compra> obtenerDetallesCompra() {
        List<Detall_Compra> detallesCompra = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );

            String query = "SELECT * FROM detall_compra";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int compraId = resultSet.getInt("compra_id");
                int productoId = resultSet.getInt("producte_id");
                double pvp = resultSet.getDouble("pvp");
                double pes = resultSet.getDouble("pes");
                int unitatsProducte = resultSet.getInt("unitats_producte");

                Detall_Compra detallCompra = new Detall_Compra(compraId, productoId, pvp, pes, unitatsProducte);
                detallesCompra.add(detallCompra);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detallesCompra;
    }

    @Override
    public void eliminarDetallCompra(Detall_Compra detalleEliminar) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );

            String query = "DELETE FROM detall_compra WHERE producte_id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, detalleEliminar.getProducte_id());
            statement.executeUpdate();

            System.out.println("Detalle de compra eliminado correctamente.");

            statement.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


