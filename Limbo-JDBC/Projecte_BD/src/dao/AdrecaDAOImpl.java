package dao;

import model.Adreca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdrecaDAOImpl implements AdrecaDAO {
    private static String DB_USERNAME = "root";
    private static String DB_PASSWORD = "";
    private static String DB_HOST = "localhost:3307";
    private static String DB_NAME = "limbo";

    @Override
    public List<Adreca> mostrarAdreça() {
        List<Adreca> adreces = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );

            String query = "SELECT * FROM adreca";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int client_id = resultSet.getInt("client_id");
                String carrer = resultSet.getString("carrer");
                String numero = resultSet.getString("numero");
                int pis = resultSet.getInt("pis");
                String porta = resultSet.getString("porta");
                String CP = resultSet.getString("CP");
                int ciutat_id = resultSet.getInt("ciutat_id");

                Adreca adreca = new Adreca(id, client_id, carrer, numero, pis, porta, CP, ciutat_id);
                adreces.add(adreca);
            }
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adreces;
    }


    @Override
    public boolean registre(Adreca adreca) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );

            String query = "INSERT INTO adreca (client_id, carrer, numero, pis, porta, CP, ciutat_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, adreca.getClient_id());
            statement.setString(2, adreca.getCarrer());
            statement.setString(3, adreca.getNumero());
            statement.setInt(4, adreca.getPis());
            statement.setString(5, adreca.getPorta());
            statement.setString(6, adreca.getCP());
            statement.setInt(7, adreca.getCiutat_id());

            statement.executeUpdate();

            statement.close();
            con.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
