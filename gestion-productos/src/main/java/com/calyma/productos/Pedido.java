package com.calyma.productos;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contadorId = 0;
    private int idPedido;
    private List<ListaPedido> lineas;
    private double costoTotal;

    public Pedido() {
        this.idPedido = ++contadorId;
        this.lineas = new ArrayList<>();
        this.costoTotal = 0.0;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public List<ListaPedido> getLineas() {
        return lineas;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void agregarLinea(ListaPedido linea) {
        this.lineas.add(linea);
        this.costoTotal += linea.getSubtotal();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido ID: ").append(idPedido).append("\n");
        sb.append("Lineas del Pedido:\n");
        for (ListaPedido linea : lineas) {
            sb.append("- ").append(linea.getProducto().getNombre())
              .append(" x ").append(linea.getCantidad())
              .append(" @ ").append(linea.getProducto().getPrecio())
              .append(" (Subtotal: ").append(linea.getSubtotal()).append(")\n");
        }
        sb.append("Costo Total del Pedido: ").append(costoTotal);
        return sb.toString();
    }
} 