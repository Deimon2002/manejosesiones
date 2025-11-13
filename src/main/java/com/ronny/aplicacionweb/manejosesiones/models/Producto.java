package com.ronny.aplicacionweb.manejosesiones.models;
/*
DESARROLLADOR: Ronny Bastidas
DESCRIPCION:es una clase modelo que representa los datos basicos como el id, nombre, tipo, precio
se usa como modelo de datos para mover informacion entre capas
*/
public class Producto {
    // Atributos simples del producto
    private Long id;
    private String nombre;
    private String tipo;
    private int precio;

    public Producto() {
    }

    // Constructor completo para crear objetos de forma directa
    public Producto(Long id, String nombre, String tipo, int precio) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
    }
    // Getters y Setters (encapsulación)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }
}

