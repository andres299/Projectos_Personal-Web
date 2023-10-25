package dao;

import model.Adreca;

import java.util.List;

public interface AdrecaDAO {
    List<Adreca> mostrarAdreça();

    boolean registre(Adreca adreca);
}
