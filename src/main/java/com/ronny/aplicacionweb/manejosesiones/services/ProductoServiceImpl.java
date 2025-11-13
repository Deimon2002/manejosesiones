package com.ronny.aplicacionweb.manejosesiones.services;

import com.ronny.aplicacionweb.manejosesiones.models.Producto; // (Importe su clase Producto)
import java.util.Arrays;
import java.util.List;

public class ProductoServiceImpl implements ProductoService {
    @Override   //Cumple el contrato de ProductoService
    public List<Producto> listar() {
        // En un proyecto real, esto vendría de una base de datos.
        // Aquí simplemente se creo una lista fija para la demostración.
        return Arrays.asList(
                new Producto(1L, "Laptop IA Pro", "Computacion", 1500),
                new Producto(2L, "Teclado Mecanico", "Computacion", 120),
                new Producto(3L, "Mouse Vertical", "Computacion", 80)
        );
    }
}