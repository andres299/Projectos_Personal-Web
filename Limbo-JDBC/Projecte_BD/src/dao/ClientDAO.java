package dao;

import model.Client;

import java.util.List;

public interface ClientDAO {
    public void registre(Client client);

    public Client login(String username, String password);

    boolean existeUsuario(String username);

    List<Client> getClientList();

    Client loginDNI(int clientNumber, String password);

    Client datosUsuario(int client);

    void modificarAtributosCliente(String username, String nuevoNom, String nuevoCognom1, String nuevoCognom2);

    boolean cambiarContraseña(String username, String contraseñaActual, String nuevaContraseña1);

    Client datosUsuarioPersonal(String username);
}
