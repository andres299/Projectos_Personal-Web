package dao;

import model.Categoria;
import model.Producte;

import java.util.List;

public interface ProducteDAO {
    List<Producte> mostrarTodosProductos();

    List<Producte> obtenerProductosSugeridos();

    List<Producte> filtrarPorNombre(String valorFiltro);

    List<Producte> filtrarPorDescripcion(String valorFiltro);

    List<Producte> filtrarPorMarca(String valorFiltro);

    List<Producte> filtrarPorCategoria(String categoriaId);

    Producte obtenerProductoPorId(int producteId);
}
