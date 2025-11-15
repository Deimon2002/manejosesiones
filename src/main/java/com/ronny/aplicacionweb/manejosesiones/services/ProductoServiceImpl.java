package com.ronny.aplicacionweb.manejosesiones.services;
/*
DESARROLLADOR: Ronny Bastidas
DESCRIPCION: en esta clase solo se simula un servicio de productos fija ya que la app no esta
conectada a una base de datos
*/
import com.ronny.aplicacionweb.manejosesiones.models.Producto; // (Importe su clase Producto)
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoService {

    /*
     * Retorna la lista completa de productos disponibles.
     * En producción, esto debería consultar una base de datos.
     */
    @Override
    public List<Producto> listar() {
        return Arrays.asList(
                new Producto(1L, "Laptop IA Pro", "Computacion", 1500),
                new Producto(2L, "Teclado Mecanico", "Computacion", 120),
                new Producto(3L, "Mouse Vertical", "Computacion", 80)
        );
    }

    /*
     * Busca un producto específico por su ID.
     * @param id El identificador único del producto
     * @return Optional con el producto si existe, vacío si no
     */
    @Override
    public Optional<Producto> porId(Long id) {
        return listar().stream()
                .filter(producto -> producto.getId().equals(id))
                .findAny();
    }
}