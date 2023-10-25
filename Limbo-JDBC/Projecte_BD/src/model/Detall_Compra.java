package model;

public class Detall_Compra {
    int compra_id;
    int producte_id;
    double pvp;
    double pes;
    int unitats_producte;

    public Detall_Compra(int compra_id, int producte_id, double pvp, int unitats_producte) {
        this.compra_id = compra_id;
        this.producte_id = producte_id;
        this.pvp = pvp;
        this.unitats_producte = unitats_producte;
    }

    public Detall_Compra(int compra_detall, int producte_id, double pvp, double pes, int unitats_producte) {
        this.compra_id = compra_detall;
        this.producte_id = producte_id;
        this.pvp = pvp;
        this.pes = pes;
        this.unitats_producte = unitats_producte;
    }

    public int getCompra_id() {
        return compra_id;
    }

    public void setCompra_id(int compra_id) {
        this.compra_id = compra_id;
    }

    public int getProducte_id() {
        return producte_id;
    }

    public void setProducte_id(int producte_id) {
        this.producte_id = producte_id;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public double getPes() {
        return pes;
    }

    public void setPes(Double pes) {
        this.pes = pes;
    }

    public int getUnitats_producte() {
        return unitats_producte;
    }

    public void setUnitats_producte(int unitats_producte) {
        this.unitats_producte = unitats_producte;
    }

    public void setIdCompra(int compraId) {
    }
}
