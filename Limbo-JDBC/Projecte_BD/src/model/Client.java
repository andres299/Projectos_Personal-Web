package model;

public class Client {
    int numero_client;
    String email;
    String nom;
    String cognom1;
    String cognom2;
    String username;
    String contrasenya;

    public Client() {
    }

    public Client(String email, String nom, String cognom1, String cognom2, String username, String contrasenya) {
        this.email = email;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.username = username;
        this.contrasenya = contrasenya;
    }

    public Client(int numero_client, String email, String nom, String cognom1, String cognom2, String username, String contrasenya) {
        this.numero_client = numero_client;
        this.email = email;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.username = username;
        this.contrasenya = contrasenya;
    }

    public Client(int numero_client, String username) {
        this.numero_client = numero_client;
        this.username = username;
    }

    public int getNumero_client() {
        return numero_client;
    }

    public void setNumero_client(int numero_client) {
        this.numero_client = numero_client;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }


}
