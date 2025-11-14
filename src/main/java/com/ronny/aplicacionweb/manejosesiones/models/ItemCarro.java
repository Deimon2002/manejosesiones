package com.ronny.aplicacionweb.manejosesiones.models;

import java.util.Objects;

/**
 * Clase que representa un item individual dentro del carrito de compras.
 * Encapsula un producto junto con la cantidad solicitada.
 */
public class ItemCarro {

    /**
     * Cantidad de unidades del producto en el carrito.
     */
    private int cantidad;

    /**
     * Referencia al producto asociado a este item.
     */
    private Producto producto;

    /**
     * Constructor que inicializa un item del carrito con cantidad y producto.
     *
     * @param cantidad Número de unidades del producto
     * @param producto Objeto Producto a agregar al carrito
     */
    public ItemCarro(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    /**
     * Obtiene la cantidad de unidades del producto.
     *
     * @return Cantidad actual del item
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece una nueva cantidad para el item.
     * Usado cuando se incrementa la cantidad de un producto ya existente en el carrito.
     *
     * @param cantidad Nueva cantidad a establecer
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el producto asociado a este item.
     *
     * @return Objeto Producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto para este item.
     *
     * @param producto Nuevo producto a asociar
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Compara dos items del carrito para determinar si son iguales.
     * Dos items se consideran iguales si tienen el mismo ID de producto
     * y la misma cantidad.
     * Este método es usado para detectar si un producto ya existe en el carrito
     * y evitar duplicados, incrementando la cantidad en su lugar.
     *
     * @param o Objeto a comparar
     * @return true si los items son iguales (mismo producto y cantidad), false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        // Verifica si es la misma referencia en memoria
        if (this == o) return true;

        // Verifica si el objeto es nulo o de diferente clase
        if (o == null || getClass() != o.getClass()) return false;

        // Castea el objeto a ItemCarro para comparar
        ItemCarro itemCarro = (ItemCarro) o;

        // Compara si tienen el mismo ID de producto y la misma cantidad
        return Objects.equals(producto.getId(), itemCarro.producto.getId())
                && Objects.equals(cantidad, itemCarro.cantidad);
    }

    /**
     * Calcula el subtotal del item multiplicando cantidad por precio del producto.
     *
     * @return Subtotal del item (cantidad * precio unitario)
     */
    public double getSubtotal() {
        return cantidad * producto.getPrecio();  // NOTA: Debería ser multiplicación (*)
    }
}