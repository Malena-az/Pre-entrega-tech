package com.calyma.productos;
//ver bien lo de abstract
public abstract class Producto {
    private static int contadorId = 0;
    protected String id;
    protected String nombre;
    protected double precio;
    protected int stock;
    protected String categoria;

    public Producto(String nombre, double precio,int stock, String categoria)
    {
        this.id = String.valueOf(++contadorId);
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public String getId() {return id;}
    public String getNombre() {return nombre;}
    public double getPrecio() {return precio;}
    public int getStock() {return stock;}
    public String getCategoria() {return categoria;}

    public void setStock(int stock) {this.stock = stock; }
    public void setPrecio(double precio) {this.precio = precio; }

    public abstract String mostrarDetallesEspecificos();
}
