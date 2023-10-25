package model;

import java.util.Date;

public class Targeta {
    String id;
    String tipus;
    int numero;
    Date data_caducitat;
    int codi_seguretat;
    int client_id;

    public Targeta(String id, String tipus, int numero, Date data_caducitat, int codi_seguretat, int client_id) {
        this.id = id;
        this.tipus = tipus;
        this.numero = numero;
        this.data_caducitat = data_caducitat;
        this.codi_seguretat = codi_seguretat;
        this.client_id = client_id;
    }

    public Targeta(int numero, String tipus, Date data_caducitat) {
        this.numero = numero;
        this.tipus = tipus;
        this.data_caducitat = data_caducitat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getData_caducitat() {
        return data_caducitat;
    }

    public void setData_caducitat(Date data_caducitat) {
        this.data_caducitat = data_caducitat;
    }

    public int getCodi_seguretat() {
        return codi_seguretat;
    }

    public void setCodi_seguretat(int codi_seguretat) {
        this.codi_seguretat = codi_seguretat;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    @Override
    public String toString() {
        return "Número: " + numero + ", Tipo: " + tipus + ", Fecha de caducidad: " + data_caducitat;
    }
}