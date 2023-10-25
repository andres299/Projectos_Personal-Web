package dao;

import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    private static String DB_USERNAME = "root";
    private static String DB_PASSWORD = "";
    private static String DB_HOST = "localhost:3307";
    private static String DB_NAME = "limbo";

    @Override
    public void registre(Client client) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            String verificacionQuery = "SELECT * FROM client WHERE username = ? OR email = ?";
            PreparedStatement verificacionStmt = con.prepareStatement(verificacionQuery);
            verificacionStmt.setString(1, client.getUsername());
            verificacionStmt.setString(2, client.getEmail());
            ResultSet resultSet = verificacionStmt.executeQuery();

            if (resultSet.next()) {
                System.out.println("El usuario o el correo electrónico ya están registrados");
                return;
            }

            String consulta = "INSERT INTO `client` (`email`, `nom`, `cognom1`, `cognom2`, `username`, `contrasenya`) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(consulta);
            stmt.setString(1, client.getEmail());
            stmt.setString(2, client.getNom());
            stmt.setString(3, client.getCognom1());
            stmt.setString(4, client.getCognom2());
            stmt.setString(5, client.getUsername());
            stmt.setString(6, client.getContrasenya());

            System.out.println("Inserint a la BBDD: " + consulta);
            stmt.executeUpdate();
            System.out.println("Se ha registrado correctamente");

            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Client login(String username, String password) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            String consulta = "SELECT username, contrasenya FROM client WHERE username=? AND contrasenya=?";
            PreparedStatement stmt = con.prepareStatement(consulta);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String retrievedUsername = rs.getString("username");
                String retrievedPassword = rs.getString("contrasenya");
                Client client = new Client();
                client.setUsername(retrievedUsername);
                client.setContrasenya(retrievedPassword);
                client.setUsername(username);

                stmt.close();
                con.close();
                return client;
            } else {
                System.out.println("Inicio de sesión fallido");
            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean existeUsuario(String username) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            String consulta = "SELECT COUNT(*) AS count FROM client WHERE username = ?";
            PreparedStatement stmt = con.prepareStatement(consulta);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                stmt.close();
                con.close();
                return count > 0; // Devuelve true si el contador es mayor a 0, indicando que el usuario existe
            }

            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Client> getClientList() {
        List<Client> clients = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            String query = "SELECT numero_client, username FROM client";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int numero_client = resultSet.getInt("numero_client");
                String username = resultSet.getString("username");

                Client client = new Client(numero_client, username);
                clients.add(client);
            }

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public Client loginDNI(int clientNumber, String password) {
        Client client = null;
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            String query = "SELECT * FROM client WHERE numero_client = ? AND contrasenya = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, clientNumber);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int numero_client = resultSet.getInt("numero_client");
                String username = resultSet.getString("username");
                client = new Client(numero_client, username);
            }

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Client datosUsuario(int client) {
        Client client1 = null;
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            String query = "SELECT nom, cognom1, cognom2, email FROM client WHERE numero_client = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, client);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                client1 = new Client();
                client1.setNom(resultSet.getString("nom"));
                client1.setCognom1(resultSet.getString("cognom1"));
                client1.setCognom2(resultSet.getString("cognom2"));
                client1.setEmail(resultSet.getString("email"));
            }

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client1;
    }

    @Override
    public void modificarAtributosCliente(String username, String nuevoNom, String nuevoCognom1, String nuevoCognom2) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            String query = "UPDATE client SET nom = ?, cognom1 = ?, cognom2 = ? WHERE username = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, nuevoNom);
            pstmt.setString(2, nuevoCognom1);
            pstmt.setString(3, nuevoCognom2);
            pstmt.setString(4, username);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Los atributos del cliente han sido modificados correctamente.");
                System.out.println("Se ha enviado un email al usuario " + username);
            } else {
                System.out.println("No se encontró ningún cliente con el nombre de usuario especificado.");
            }
            pstmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean cambiarContraseña(String username, String contraseñaActual, String nuevaContraseña1) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );

            PreparedStatement stmt = con.prepareStatement("SELECT contrasenya FROM client WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String contraseñaGuardada = rs.getString("contrasenya");
                if (contraseñaGuardada.equals(contraseñaActual)) {
                    stmt = con.prepareStatement("UPDATE client SET contrasenya = ? WHERE username = ?");
                    stmt.setString(1, nuevaContraseña1);
                    stmt.setString(2, username);
                    stmt.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Client datosUsuarioPersonal(String username) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://" + DB_HOST + "/" + DB_NAME,
                    DB_USERNAME, DB_PASSWORD
            );
            String sql = "SELECT numero_client, email, nom, cognom1, cognom2, username, contrasenya " +
                    "FROM client " +
                    "WHERE username = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int numero_client = rs.getInt("numero_client");
                String email = rs.getString("email");
                String nom = rs.getString("nom");
                String cognom1 = rs.getString("cognom1");
                String cognom2 = rs.getString("cognom2");
                String contrasenya = rs.getString("contrasenya");

                Client cliente = new Client(numero_client, email, nom, cognom1, cognom2, username, contrasenya);

                return cliente;
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}


