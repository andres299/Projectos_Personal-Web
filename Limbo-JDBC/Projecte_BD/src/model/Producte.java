package model;

public class Producte {
    int id;
    String nom;
    String descripcio;
    Double pvp;
    int iva;
    String marca;
    String unitat_mesura;
    Double pes;
    int categoria;

    public Producte(int id, String nom, String descripcio, Double pvp, int iva, String marca, String unitat_mesura, Double pes, int categoria) {
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.pvp = pvp;
        this.iva = iva;
        this.marca = marca;
        this.unitat_mesura = unitat_mesura;
        this.pes = pes;
        this.categoria = categoria;
    }

    public Producte(int id, String nom, double pvp) {
        this.id = id;
        this.nom = nom;
        this.pvp = pvp;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Double getPvp() {
        return pvp;
    }

    public void setPvp(Double pvp) {
        this.pvp = pvp;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUnitat_mesura() {
        return unitat_mesura;
    }

    public void setUnitat_mesura(String unitat_mesura) {
        this.unitat_mesura = unitat_mesura;
    }

    public Double getPes() {
        return pes;
    }

    public void setPes(Double pes) {
        this.pes = pes;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}

