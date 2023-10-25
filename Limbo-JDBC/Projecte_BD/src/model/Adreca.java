package model;

public class Adreca {
    int id;
    int client_id;
    String carrer;
    String numero;
    int pis;
    String porta;
    String CP;
    int ciutat_id;

    public Adreca(int id, int client_id, String carrer, String numero, int pis, String porta, String CP, int ciutat_id) {
        this.id = id;
        this.client_id = client_id;
        this.carrer = carrer;
        this.numero = numero;
        this.pis = pis;
        this.porta = porta;
        this.CP = CP;
        this.ciutat_id = ciutat_id;
    }

    public Adreca(int client_id, String carrer, String numero, int pis, String porta, String CP, int ciutat_id) {
        this.client_id = client_id;
        this.carrer = carrer;
        this.numero = numero;
        this.pis = pis;
        this.porta = porta;
        this.CP = CP;
        this.ciutat_id = ciutat_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getCarrer() {
        return carrer;
    }

    public void setCarrer(String carrer) {
        this.carrer = carrer;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getPis() {
        return pis;
    }

    public void setPis(int pis) {
        this.pis = pis;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public int getCiutat_id() {
        return ciutat_id;
    }

    public void setCiutat_id(int ciutat_id) {
        this.ciutat_id = ciutat_id;
    }

    @Override
    public String toString() {
        return "Carrer: " + carrer + ", Número: " + numero + ", Porta: " + porta;
    }
}
