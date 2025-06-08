package com.calyma.productos;

public class ProductoTextil extends Producto {
    private String aromas;
    private String ContenidoNeto;

    public ProductoTextil(String nombre, double precio, int stock, String categoria, String aromas, String ContenidoNeto){
        super(nombre,precio,stock,categoria);
        this.aromas = aromas;
        this.ContenidoNeto = ContenidoNeto;
    }

    public String getAroma() {return aromas;}
    public String getContenidoNeto() {return ContenidoNeto;}

    @Override
    public String mostrarDetallesEspecificos() {
        return String.format("Aroma: %s, Contenido Neto: %s",aromas, ContenidoNeto);
    }
    
}
