package dao;

import model.Targeta;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TargetaDAOImpl implements TargetaDAO {
    private static String DB_USERNAME = "root";
    private static String DB_PASSWORD = "";
    private static String DB_HOST = "localhost:3307";
    private static String DB_NAME = "limbo";

    @Override
    public void registre(String tipus, int numero, Date data_caducitat, int codi_seguretat, int client_id) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );

            String sql = "INSERT INTO targeta (tipus, numero, data_caducitat, codi_seguretat, client_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, tipus);
            stmt.setInt(2, numero);
            stmt.setDate(3, new java.sql.Date(data_caducitat.getTime()));
            stmt.setInt(4, codi_seguretat);
            stmt.setInt(5, client_id);
            stmt.executeUpdate();

            stmt.close();
            con.close();

            System.out.println("Tarjeta registrada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Targeta> mostrarTargetas() {
        List<Targeta> targetas = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );

            String query = "SELECT * FROM targeta";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String tipus = resultSet.getString("tipus");
                int numero = resultSet.getInt("numero");
                Date data_caducitat = resultSet.getDate("data_caducitat");
                int codi_seguretat = resultSet.getInt("codi_seguretat");
                int client_id = resultSet.getInt("client_id");

                Targeta targeta = new Targeta(id, tipus, numero, data_caducitat, codi_seguretat, client_id);
                targetas.add(targeta);
            }

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return targetas;
    }

    @Override
    public boolean eliminarTarjeta(String id_tarjeta) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );

            String query = "DELETE FROM targeta WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, id_tarjeta);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
