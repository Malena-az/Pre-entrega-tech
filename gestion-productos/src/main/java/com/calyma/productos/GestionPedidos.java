package com.calyma.productos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.calyma.excepciones.StockInsuficienteException;


public class GestionPedidos {
    private List<Pedido> pedidos;
    private GestionProductos gestionProductos;
    private Scanner scanner;

    public GestionPedidos(GestionProductos gestionProductos, Scanner scanner) {
        this.pedidos = new ArrayList<>();
        this.gestionProductos = gestionProductos;
        this.scanner = scanner;
    }

    public void crearNuevoPedido() {
        Pedido nuevoPedido = new Pedido();
        boolean agregarMasProductos = true;

        System.out.println("\n--- Creando Nuevo Pedido ---");

        while (agregarMasProductos) {
            gestionProductos.listarTodosProductos().forEach(p -> System.out.println("ID: " + p.getId() + ", Nombre: " + p.getNombre() + ", Stock: " + p.getStock()));
            System.out.print("Ingrese el ID del producto a agregar (o 'fin' para terminar): ");
            String idProducto = scanner.nextLine();

            if (idProducto.equalsIgnoreCase("fin")) {
                agregarMasProductos = false;
                break;
            }

            Optional<Producto> productoOpt = gestionProductos.buscarProductoPorId(idProducto);

            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                System.out.print("Ingrese la cantidad deseada: ");
                int cantidad = scanner.nextInt();
                scanner.nextLine();

                if (cantidad <= 0) {
                    System.out.println("La cantidad debe ser mayor a cero.");
                    continue;
                }

                if (producto.getStock() >= cantidad) {
                    nuevoPedido.agregarLinea(new ListaPedido(producto, cantidad));
                    try {
                        gestionProductos.actualizarStock(producto.getId(), producto.getStock() - cantidad);
                        System.out.println("Producto agregado al pedido.");
                    } catch (StockInsuficienteException e) {
                        System.out.println("Error de stock inesperado: " + e.getMessage());
                    }
                } else {
                    System.out.println("Stock insuficiente. Disponible: " + producto.getStock());
                }
            } else {
                System.out.println("Producto con ID " + idProducto + " no encontrado.");
            }

            System.out.print("¿Desea agregar otro producto? (si/no): ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("si")) {
                agregarMasProductos = false;
            }
        }

        if (!nuevoPedido.getLineas().isEmpty()) {
            pedidos.add(nuevoPedido);
            System.out.println("Pedido creado con exito. Costo Total: " + nuevoPedido.getCostoTotal());
        } else {
            System.out.println("Pedido cancelado o sin productos.");
        }
    }

    public void listarTodosPedidos() {
        System.out.println("\n--- Listado de Pedidos ---");
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
                System.out.println("--------------------");
            }
        }
    }
} 