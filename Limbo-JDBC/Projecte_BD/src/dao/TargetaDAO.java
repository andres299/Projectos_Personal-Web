package dao;

import model.Targeta;

import java.util.Date;
import java.util.List;

public interface TargetaDAO {
    default void registre(String tipus, int numero, Date data_caducitat, int codi_seguretat, int client_id) {
    }

    List<Targeta> mostrarTargetas();

    boolean eliminarTarjeta(String id_tarjeta);
}
