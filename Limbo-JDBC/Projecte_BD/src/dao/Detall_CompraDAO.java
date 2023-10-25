package dao;

import model.Detall_Compra;

import java.util.List;

public interface Detall_CompraDAO {
    void guardarDetall_Compra(Detall_Compra detallCompra);

    String totalPVP();

    List<Detall_Compra> obtenerDetallesCompra();

    void eliminarDetallCompra(Detall_Compra detalleEliminar);
}
