package com.calyma;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.calyma.excepciones.StockInsuficienteException;
import com.calyma.productos.Producto;
import com.calyma.productos.ProductoAceitesHornillos;
import com.calyma.productos.ProductoAromatico;
import com.calyma.productos.ProductoTextil;
import com.calyma.productos.ProductoDifusores;
import com.calyma.productos.HornillosVelas;
import com.calyma.productos.GestionPedidos;
import com.calyma.productos.GestionProductos;

public class Main {
    public static void main(String[] arg) {
        GestionProductos gestionProductos = new GestionProductos();
        Scanner scanner = new Scanner(System.in);
        GestionPedidos gestionPedidos = new GestionPedidos(gestionProductos, scanner);

        // Productos aromaticos
        gestionProductos.agregarProducto(new ProductoAromatico("Sahumerio Lavanda", 1500, 50, "Sahumerios Premium", "Lavanda"));
        gestionProductos.agregarProducto(new ProductoAromatico("Incienso Rosas", 1200, 30, "Inciensos", "Rosas"));
        // Productos textiles
        gestionProductos.agregarProducto(new ProductoTextil("Textil Invicto Legend", 3400, 50, "Textiles", "Marino,Pomelo,Lavanda,Laurel", "250ml"));
        gestionProductos.agregarProducto(new ProductoTextil("Textil Salvaje", 3400, 30, "Textiles", "Bergamota de Calabria, pimienta, lavanda, cedro y ládano", "250ml"));
        // Productos aceites
        gestionProductos.agregarProducto(new ProductoAceitesHornillos("Aceite para hornillo Cedro",3140,50,"Aceites","Mandarina,lavanda,cedro,nuez moscada","10ml"));
        gestionProductos.agregarProducto(new ProductoAceitesHornillos("Aceite para hornillo Ruda y Romero",3140,45,"Aceites","Romero,Madera de sándalo,citricos","10ml" ));
        // Productos difusores
        gestionProductos.agregarProducto(new ProductoDifusores("Difusor Sandia Pepino", 4890,60,"Difusres","sandia,manzana verde, duranzno,vainilla,ambar","125ml"));
        gestionProductos.agregarProducto(new ProductoDifusores("Difusor Cookies & Cream",4890,50,"Disfusores","coco,gourmand,vainilla, ambar","125ml"));


        int opcion = 0;
        while (opcion != 7) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        agregarProductoDesdeConsola(scanner, gestionProductos);
                        break;
                    case 2:
                        System.out.println("\n--- Listado de Productos ---");
                        gestionProductos.listarTodosProductos().forEach(p -> {
                            System.out.println("ID: " + p.getId() + ", Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio() + ", Stock: " + p.getStock());
                        });
                        break;
                    case 3:
                        buscarActualizarProducto(scanner, gestionProductos);
                        break;
                    case 4:
                        eliminarProducto(scanner, gestionProductos);
                        break;
                    case 5:
                        gestionPedidos.crearNuevoPedido();
                        break;
                    case 6:
                        gestionPedidos.listarTodosPedidos();
                        break;
                    case 7:
                        System.out.println("Saliendo del programa...Hasta pronto");
                        break;
                    default:
                        System.out.println("Opción no valida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no valida. Por favor ingrese un número.");
            } catch (Exception e) {
                System.out.println("Ocurrio un error: " + e.getMessage());
            }
            System.out.println();
        }

        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("\nCalyma – Ritual de esencia y calma");
        System.out.println("--- Menú Principal ---");
        System.out.println("1. Agregar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar/Actualizar producto");
        System.out.println("4. Eliminar producto");
        System.out.println("5. Crear un pedido");
        System.out.println("6. Listar pedidos");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public static void buscarActualizarProducto(Scanner scanner, GestionProductos gestionProductos) {
        System.out.println("\n--- Buscar/Actualizar Producto ---");
        System.out.print("Seleccione si quiere buscar por (ID/Nombre): ");
        String tipoBusqueda = scanner.nextLine();

        if (tipoBusqueda.equalsIgnoreCase("ID")) {
            System.out.print("Ingrese el ID del producto: ");
            String id = scanner.nextLine();
            Optional<Producto> productoOpt = gestionProductos.buscarProductoPorId(id);

            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                System.out.println("Producto encontrado: ID: " + producto.getId() + ", Nombre: " + producto.getNombre() + ", Precio: " + producto.getPrecio() + ", Stock: " + producto.getStock());

                System.out.print("¿Desea actualizar el stock o el precio? (stock/precio/no): ");
                String opcionActualizar = scanner.nextLine();

                if (opcionActualizar.equalsIgnoreCase("stock")) {
                    System.out.print("Ingrese el nuevo stock: ");
                    try {
                        int nuevoStock = Integer.parseInt(scanner.nextLine());
                        gestionProductos.actualizarStock(id, nuevoStock);
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada de stock no valida.");
                    } catch (StockInsuficienteException e) {
                        System.out.println("Error al actualizar stock: " + e.getMessage());
                    }
                } else if (opcionActualizar.equalsIgnoreCase("precio")) {
                     System.out.print("Ingrese el nuevo precio: ");
                    try {
                        double nuevoPrecio = Double.parseDouble(scanner.nextLine());
                        gestionProductos.actualizarPrecio(id, nuevoPrecio);
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada de precio no es valida.");
                    }
                }

            } else {
                System.out.println("Producto con ID " + id + " no encontrado.");
            }
        } else if (tipoBusqueda.equalsIgnoreCase("Nombre")) {
            System.out.print("Ingrese el nombre del producto: ");
            String nombre = scanner.nextLine();
            List<Producto> productosEncontrados = gestionProductos.buscarProductoPorNombre(nombre);

            if (!productosEncontrados.isEmpty()) {
                System.out.println("Productos encontrados con el nombre \"" + nombre + "\":");
                productosEncontrados.forEach(p -> System.out.println("ID: " + p.getId() + ", Nombre: " + p.getNombre() + ", Precio: " + p.getPrecio() + ", Stock: " + p.getStock()));
                System.out.println("Para actualizar use la opción de busqueda por ID.");
            } else {
                System.out.println("No se encontraron productos con el nombre \"" + nombre + "\".");
            }
        } else {
            System.out.println("Tipo de busqueda no valido.");
        }
    }

    public static void eliminarProducto(Scanner scanner, GestionProductos gestionProductos) {
        System.out.println("\n--- Eliminar Producto ---");
        System.out.print("Eliminar por (ID/Posicion): ");
        String tipoEliminacion = scanner.nextLine();

        if (tipoEliminacion.equalsIgnoreCase("ID")) {
            System.out.print("Ingrese el ID del producto que quiera eliminar: ");
            String id = scanner.nextLine();
            boolean eliminado = gestionProductos.eliminarProductoPorId(id);
            if (eliminado) {
                System.out.println("Producto con ID " + id + " eliminado.");
            } else {
                System.out.println("Producto con ID " + id + " no encontrado.");
            }
        } else if (tipoEliminacion.equalsIgnoreCase("Posicion")) {
             System.out.print("Ingrese la posición del producto que quiera eliminar (empezando en 0): ");
            try {
                int posicion = Integer.parseInt(scanner.nextLine());
                boolean eliminado = gestionProductos.eliminarProductoPorPosicion(posicion);
                 if (eliminado) {
                    System.out.println("Producto en posición " + posicion + " eliminado.");
                } else {
                    System.out.println("La posición no es valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada de posición no válida.");
            }
        } else {
            System.out.println("No valido.");
        }
    }

    public static void agregarProductoDesdeConsola(Scanner scanner, GestionProductos gestionProductos) {
        System.out.println("\n--- Agregar Nuevo Producto ---");
        System.out.print("Ingrese nombre del producto: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Ingrese precio: ");
        double precio = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Ingrese stock inicial: ");
        int stock = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Ingrese categoría: ");
        String categoria = scanner.nextLine();
        
        System.out.println("\nSeleccione el tipo de producto:");
        System.out.println("1. Producto Aromático");
        System.out.println("2. Producto Textil");
        System.out.println("3. Producto Aceites para Hornillos");
        System.out.println("4. Producto Difusores");
        System.out.println("5. Produsctos hornillos");
        System.out.print("Opción: ");
        
        int tipoProducto = Integer.parseInt(scanner.nextLine());
        Producto nuevoProducto = null;

        // TENGO QUE AGREGAR MAS CASOS SI AGREGO OTROS PRODUCTOS
        switch (tipoProducto) {
            case 1:
                System.out.print("Ingrese aroma: ");
                String aroma = scanner.nextLine();
                nuevoProducto = new ProductoAromatico(nombre, precio, stock, categoria, aroma);
                break;
                
            case 2:
                System.out.print("Ingrese fragancia: ");
                String fragancia = scanner.nextLine();
                System.out.print("Ingrese capacidad: ");
                String capacidad = scanner.nextLine();
                nuevoProducto = new ProductoTextil(nombre, precio, stock, categoria, fragancia, capacidad);
                break;
                
            case 3:
                System.out.print("Ingrese fragancia: ");
                String fraganciaAceite = scanner.nextLine();
                System.out.print("Ingrese capacidad: ");
                String capacidadAceite = scanner.nextLine();
                nuevoProducto = new ProductoAceitesHornillos(nombre, precio, stock, categoria, fraganciaAceite, capacidadAceite);
            case 4:
                System.out.print("Ingrese fragancia: ");
                String fraganciaDifusor = scanner.nextLine();
                System.out.print("Ingrese capacidad: ");
                String capacidadDifusor = scanner.nextLine();
                nuevoProducto = new ProductoDifusores(nombre, precio, stock, categoria, fraganciaDifusor, capacidadDifusor);
            case 5:
                System.out.print("Ingrese Tipo: ");
                String TipoHornillo = scanner.nextLine();
                nuevoProducto = new HornillosVelas(nombre, precio, stock, categoria, TipoHornillo);
                break;
                
            default:
                System.out.println("Tipo de producto no válido.");
                return;
        }
        
        gestionProductos.agregarProducto(nuevoProducto);
        System.out.println("Producto agregado con éxito.");
    }
}