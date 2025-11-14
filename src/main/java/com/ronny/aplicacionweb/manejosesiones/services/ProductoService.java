package com.ronny.aplicacionweb.manejosesiones.services;

import com.ronny.aplicacionweb.manejosesiones.models.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<Producto> listar(); //método que devuelve la lista de productos
    Optional<Producto> porId(Long id);
}