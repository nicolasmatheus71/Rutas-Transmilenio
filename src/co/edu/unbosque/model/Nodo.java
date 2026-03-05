package co.edu.unbosque.model;
import java.util.*;

public class Nodo {
    private String nombre;
    private List<Nodo> adyacentes;

    public Nodo(String nombre) {
        this.nombre = nombre;
        this.adyacentes = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Nodo> getAdyacentes() {
        return adyacentes;
    }

    public void agregarAdyacente(Nodo nodo) {
        if (!adyacentes.contains(nodo)) {
            adyacentes.add(nodo);
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}
