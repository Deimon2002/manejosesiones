package com.ronny.aplicacionweb.manejosesiones.models;

public class Producto {
    // Atributos simples del producto
    private Long id;
    private String nombre;
    private String tipo;
    private int precio; // Puede ser int para demo; en dinero real se prefiere BigDecimal

    // Constructor vacío (necesario para frameworks/serialización)
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

