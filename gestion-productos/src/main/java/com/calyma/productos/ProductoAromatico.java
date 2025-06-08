package com.calyma.productos;

public class ProductoAromatico extends Producto {
    private String aroma;

    public ProductoAromatico(String nombre, double precio,
                               int stock, String categoria, String aroma){
        super(nombre,precio,stock,categoria);
        this.aroma = aroma;
    }

    public String getAroma() {return aroma;}

    @Override
    public String mostrarDetallesEspecificos() {
        return String.format("Aroma: %s",aroma);
    }
}
