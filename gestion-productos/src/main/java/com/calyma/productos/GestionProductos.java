package com.calyma.productos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.calyma.excepciones.StockInsuficienteException;
import com.calyma.productos.Producto;

public class GestionProductos {
    private List<Producto>productos;

    public GestionProductos() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        System.out.println("Producto " + producto.getNombre() + " agregado con ID: " + producto.getId());
    }

    public List<Producto> listarTodosProductos() {
        return new ArrayList<>(productos);
    }

    public Optional<Producto> buscarProductoPorId(String id) {
        return productos.stream()
                        .filter(p -> p.getId().equals(id))
                        .findFirst();
    }

    public List<Producto> buscarProductoPorNombre(String nombre) {
        return productos.stream()
                        .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                        .collect(Collectors.toList());
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return productos.stream().filter(p ->p.getCategoria().equals(categoria)).collect(Collectors.toList()); }

    public void actualizarStock(String id, int nuevoStock) throws StockInsuficienteException{
        Optional<Producto> productoOpt = buscarProductoPorId(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            if (nuevoStock < 0) {
                throw new StockInsuficienteException("El stock no puede ser negativo");
            }
            producto.setStock(nuevoStock);
            System.out.println("Stock de " + producto.getNombre() + " (ID: " + id + ") actualizado a: " + nuevoStock);
        } else {
            System.out.println("Producto con ID " + id + " no encontrado.");
        }
    }

    public void actualizarPrecio(String id, double nuevoPrecio) {
        Optional<Producto> productoOpt = buscarProductoPorId(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            if (nuevoPrecio < 0) {
                 System.out.println("El precio no puede ser negativo.");
                 return;
            }
            producto.setPrecio(nuevoPrecio);
             System.out.println("Precio de " + producto.getNombre() + " (ID: " + id + ") actualizado a: " + nuevoPrecio);
        } else {
            System.out.println("Producto con ID " + id + " no encontrado.");
        }
    }

    public boolean eliminarProductoPorId(String id) {
        return productos.removeIf(producto -> producto.getId().equals(id));
    }

    public boolean eliminarProductoPorPosicion(int posicion) {
        if (posicion >= 0 && posicion < productos.size()) {
            productos.remove(posicion);
            return true;
        }
        return false;
    }
}
