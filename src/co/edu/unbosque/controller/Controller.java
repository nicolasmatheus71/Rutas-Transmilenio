package co.edu.unbosque.controller;

import java.util.List;
import java.util.Scanner;
import co.edu.unbosque.model.GraphBuilder;

public class Controller {
    private Scanner sc;

    public Controller() {
        sc = new Scanner(System.in);
        GraphBuilder.cargar();
    }

    public void run() {
        int opcion;
        do {
            System.out.println("\nMenú TransMilenio");
            System.out.println("1. Ver estaciones y conexiones");
            System.out.println("2. Agregar estación");
            System.out.println("3. Conectar estaciones");
            System.out.println("4. Actualizar estación");
            System.out.println("5. Eliminar estación");
            System.out.println("6. BFS: ruta más corta");
            System.out.println("7. DFS: caminos posibles");
            System.out.println("0. Salir");
            System.out.print("Seleccione opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> GraphBuilder.mostrar();
                case 2 -> agregarEstacion();
                case 3 -> conectar();
                case 4 -> actualizarEstacion();
                case 5 -> eliminarEstacion();
                case 6 -> ejecutarBFS();
                case 7 -> ejecutarDFS();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void agregarEstacion() {
        System.out.print("Nombre nueva estación: ");
        String est = sc.nextLine();
        GraphBuilder.agregarEstacion(est);
    }

    private void conectar() {
        System.out.print("Estación 1: ");
        String a = sc.nextLine();
        System.out.print("Estación 2: ");
        String b = sc.nextLine();
        GraphBuilder.conectar(a, b);
    }

    private void actualizarEstacion() {
        System.out.print("Nombre actual: ");
        String viejo = sc.nextLine();
        System.out.print("Nuevo nombre: ");
        String nuevo = sc.nextLine();
        GraphBuilder.actualizarEstacion(viejo, nuevo);
    }

    private void eliminarEstacion() {
        System.out.print("Nombre estación a eliminar: ");
        String nombre = sc.nextLine();
        GraphBuilder.eliminarEstacion(nombre);
    }

    private void ejecutarBFS() {
        System.out.print("Inicio: ");
        String inicio = sc.nextLine();
        System.out.print("Fin: ");
        String fin = sc.nextLine();
        List<String> ruta = GraphBuilder.bfs(inicio, fin);
        System.out.println("Ruta más corta: " + ruta);
    }

    private void ejecutarDFS() {
        System.out.print("Inicio: ");
        String inicio = sc.nextLine();
        System.out.print("Fin: ");
        String fin = sc.nextLine();
        List<List<String>> caminos = GraphBuilder.dfs(inicio, fin, 3);
        System.out.println("Caminos posibles:");
        caminos.forEach(System.out::println);
    }
}
