package com.calyma.productos;

public class HornillosVelas extends Producto{
    private String Tipo;

    public HornillosVelas(String nombre, double precio, int stock, String categoria, String Tipo) {
        super(nombre, precio, stock, categoria);
        this.Tipo = Tipo;
    }
    public String getTipo() {
        return Tipo;
    }
    @Override
    public String mostrarDetallesEspecificos() {
        return String.format("Tipo: %s", Tipo);
    }
}






