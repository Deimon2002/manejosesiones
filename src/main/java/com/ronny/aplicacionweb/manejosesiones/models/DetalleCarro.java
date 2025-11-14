package com.ronny.aplicacionweb.manejosesiones.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DESCIPCION:Clase que representa el detalle completo del carrito de compras.
 * Gestiona la lista de items y operaciones como agregar productos y calcular el total.
 */
public class DetalleCarro {

    /**
     * Lista que almacena todos los items (productos) agregados al carrito.
     */
    private List<ItemCarro> items;

    /**
     * Constructor que inicializa el carrito con una lista vacía de items.
     */
    public DetalleCarro() {
        this.items = new ArrayList<>();
    }

    /**
     * Agrega un item al carrito de compras.
     * Si el producto ya existe en el carrito, incrementa su cantidad en 1.
     * Si es un producto nuevo, lo agrega a la lista de items.
     *
     * @param itemCarro El item (producto con cantidad) a agregar al carrito
     */
    public void addItemCarro(ItemCarro itemCarro) {
        // Verifica si el item ya existe en el carrito (compara por producto)
        if(items.contains(itemCarro)) {
            // Busca el item existente en la lista usando streams
            Optional<ItemCarro> optionalItemCarro = items.stream()
                    .filter(i -> i.equals(itemCarro))  // Filtra por el item que coincida
                    .findAny();  // Obtiene cualquier coincidencia

            // Si encuentra el item existente
            if(optionalItemCarro.isPresent()) {
                ItemCarro i = optionalItemCarro.get();
                // Incrementa la cantidad del producto en 1
                i.setCantidad(i.getCantidad() + 1);
            }
        } else {
            // Si es un producto nuevo, lo agrega directamente a la lista
            this.items.add(itemCarro);
        }
    }

    /**
     * Obtiene la lista completa de items en el carrito.
     *
     * @return Lista de items del carrito
     */
    public List<ItemCarro> getItem() {
        return items;
    }

    /**
     * Calcula el total a pagar sumando los subtotales de todos los items.
     * Utiliza streams para realizar el cálculo de forma funcional.
     *
     * @return El monto total del carrito
     */
    public double getTotal() {
        return items.stream()
                .mapToDouble(ItemCarro::getSubtotal)  // Convierte cada item a su subtotal
                .sum();  // Suma todos los subtotales
    }
}