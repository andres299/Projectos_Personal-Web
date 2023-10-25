package model;

import java.sql.Date;
import java.util.List;

public class Compra {
    int id;
    int targeta_id;
    int adreca_id;
    int client_id;
    String id_transaccio;
    Date data;

    public Compra(String id_transaccio, Date data) {
        this.id_transaccio = id_transaccio;
        this.data = data;
    }

    public Compra(int id, int targeta_id, int adreça_id, int client_id, String id_transaccio, Date data) {
        this.id = id;
        this.targeta_id = targeta_id;
        this.adreca_id = adreça_id;
        this.client_id = client_id;
        this.id_transaccio = id_transaccio;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTargeta_id() {
        return targeta_id;
    }

    public void setTargeta_id(int targeta_id) {
        this.targeta_id = targeta_id;
    }

    public int getAdreca_id() {
        return adreca_id;
    }

    public void setAdreca_id(int adreca_id) {
        this.adreca_id = adreca_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getId_transaccio() {
        return id_transaccio;
    }

    public void setId_transaccio(String id_transaccio) {
        this.id_transaccio = id_transaccio;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
