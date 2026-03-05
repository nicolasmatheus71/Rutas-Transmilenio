package co.edu.unbosque.model;

import java.io.*;
import java.util.*;

public class GraphBuilder {

    public static List<String> nodes = new ArrayList<>();
    public static Map<String, List<String>> adj = new HashMap<>();

    private static final String FILE_ESTACIONES = "src/estaciones.txt";
    private static final String FILE_CONEXIONES = "src/conexiones.txt";


    public static void cargar() {
        cargarEstaciones();
        cargarConexiones();
    }

    private static void cargarEstaciones() {
        nodes.clear();
        adj.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_ESTACIONES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String est = linea.trim();
                if (!est.isEmpty() && !nodes.contains(est)) {
                    nodes.add(est);
                    adj.put(est, new ArrayList<>());
                }
            }
        } catch (IOException e) {
            System.out.println("No se encontró estaciones.txt");
        }
    }

    private static void cargarConexiones() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_CONEXIONES))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 2) {
                    conectar(partes[0].trim(), partes[1].trim(), false);
                }
            }
        } catch (IOException e) {
            System.out.println("No se encontró conexiones.txt");
        }
    }

    

    private static void guardarEstaciones() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_ESTACIONES))) {
            for (String est : nodes) {
                pw.println(est);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void guardarConexiones() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_CONEXIONES))) {
            Set<String> guardadas = new HashSet<>();
            for (String nodo : adj.keySet()) {
                for (String vecino : adj.get(nodo)) {
                    String edge = nodo + ";" + vecino;
                    String edgeRev = vecino + ";" + nodo;
                    if (!guardadas.contains(edge) && !guardadas.contains(edgeRev)) {
                        pw.println(edge);
                        guardadas.add(edge);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    public static void agregarEstacion(String nombre) {
        if (!nodes.contains(nombre)) {
            nodes.add(nombre);
            adj.put(nombre, new ArrayList<>());
            guardarEstaciones();
        }
    }

    public static void conectar(String a, String b) {
        conectar(a, b, true);
    }

    private static void conectar(String a, String b, boolean guardar) {
        if (nodes.contains(a) && nodes.contains(b)) {
            if (!adj.get(a).contains(b)) adj.get(a).add(b);
            if (!adj.get(b).contains(a)) adj.get(b).add(a);
            if (guardar) guardarConexiones();
        } else {
            System.out.println("Una de las estaciones no existe");
        }
    }

    public static void actualizarEstacion(String viejo, String nuevo) {
        if (nodes.contains(viejo) && !nodes.contains(nuevo)) {
            nodes.set(nodes.indexOf(viejo), nuevo);
            List<String> conexiones = adj.remove(viejo);
            adj.put(nuevo, conexiones);

            
            for (List<String> lista : adj.values()) {
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).equals(viejo)) {
                        lista.set(i, nuevo);
                    }
                }
            }
            guardarEstaciones();
            guardarConexiones();
        }
    }

    public static void eliminarEstacion(String nombre) {
        if (nodes.contains(nombre)) {
            nodes.remove(nombre);
            adj.remove(nombre);
            for (List<String> lista : adj.values()) {
                lista.remove(nombre);
            }
            guardarEstaciones();
            guardarConexiones();
        }
    }

    

    public static List<String> bfs(String inicio, String fin) {
        if (!nodes.contains(inicio) || !nodes.contains(fin)) return Collections.emptyList();

        Set<String> visitados = new HashSet<>();
        Map<String, String> padres = new HashMap<>();
        Queue<String> cola = new LinkedList<>();
        cola.add(inicio);
        visitados.add(inicio);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            if (actual.equals(fin)) break;
            for (String vecino : adj.get(actual)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    padres.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        if (!padres.containsKey(fin) && !inicio.equals(fin)) return Collections.emptyList();

        List<String> camino = new ArrayList<>();
        for (String at = fin; at != null; at = padres.get(at)) {
            camino.add(0, at);
            if (at.equals(inicio)) break;
        }
        return camino;
    }

    

    public static List<List<String>> dfs(String inicio, String fin, int limite) {
        List<List<String>> caminos = new ArrayList<>();
        dfsRec(inicio, fin, new ArrayList<>(), new HashSet<>(), caminos, limite);
        return caminos;
    }

    private static void dfsRec(String actual, String fin, List<String> camino,
                               Set<String> visitados, List<List<String>> caminos, int limite) {
        camino.add(actual);
        visitados.add(actual);

        if (actual.equals(fin)) {
            caminos.add(new ArrayList<>(camino));
        } else {
            for (String vecino : adj.get(actual)) {
                if (!visitados.contains(vecino) && caminos.size() < limite) {
                    dfsRec(vecino, fin, camino, visitados, caminos, limite);
                }
            }
        }

        camino.remove(camino.size() - 1);
        visitados.remove(actual);
    }


    public static void mostrar() {
        for (String nodo : nodes) {
            System.out.println(nodo + " -> " + adj.get(nodo));
        }
    }
}
