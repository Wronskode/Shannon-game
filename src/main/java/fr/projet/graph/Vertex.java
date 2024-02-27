package fr.projet.graph;

import javafx.util.Pair;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Accessors(chain = true)
@Data
public class Vertex {
    private List<Vertex> listNeighbors;
    private HashSet<Vertex> listNeighborsCut = new HashSet<>(); // Pour avoir un contains en O(1) et aucune répétition ?
    private HashSet<Vertex> listNeighborsPaint = new HashSet<>();
    private Pair<Integer, Integer> coords;

    public Vertex(List<Vertex> vertices, int x, int y) {
        this.listNeighbors = new ArrayList<>(vertices);
        this.coords = new Pair<>(x, y);
    }

    public Vertex(int x, int y) {
        this.listNeighbors = new ArrayList<>();
        this.coords = new Pair<>(x, y);
    }

    public void addNeighborVertex(Vertex v) {
        if (!listNeighbors.contains(v))
            listNeighbors.add(v);
        if (!v.getListNeighbors().contains(this))
            v.getListNeighbors().add(this);
    }

    public void removeNeighborVertex(Vertex v) {
        listNeighbors.remove(v);
        v.getListNeighbors().remove(this);
    }

    public int getX() {
        return getCoords().getKey();
    }

    public int getY() {
        return getCoords().getValue();
    }
    public void removeNeighborVertex(int i) {
        listNeighbors.remove(i);
    }

    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (o == null || getClass() != o.getClass()) return false;
    //     Vertex vertex = (Vertex) o;
    //     return Objects.equals(coords, vertex.coords);
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(coords);
    // }
    @Override
    public boolean equals(Object o) { 
        // à mon avis il faut garder le equals par défaut pour comparer les instances
        return super.equals(o); 
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @Override
    public String toString() {
        return "Vertex(" + coords.getKey() + ", " + coords.getValue() + ')';
    }

    public static boolean isSameCouple(Pair<Vertex, Vertex> vertex1, Pair<Vertex, Vertex> vertex2) {
        return (vertex1.getKey().equals(vertex2.getKey()) && vertex1.getValue().equals(vertex2.getValue())) ||
                (vertex1.getKey().equals(vertex2.getValue()) && vertex1.getValue().equals(vertex2.getKey()));
    }

    public void cut(Vertex v) {
        listNeighborsCut.add(v);
        v.listNeighborsCut.add(this); // si c'est cut, c'est cut dans les deux sens
    }

    public boolean isCut(Vertex v) {
        return listNeighborsCut.contains(v) 
        || v.getListNeighborsCut().contains(this);
    }

    public void paint(Vertex v) {
        listNeighborsPaint.add(v);
        v.getListNeighborsPaint().add(this);
    }

    public boolean isPainted(Vertex v) {
        return listNeighborsPaint.contains(v) || v.getListNeighborsPaint().contains(this);
    }

    public boolean isCutOrPanted(Vertex v) {
        return isCut(v) || isPainted(v);
    }

    public double distance(Vertex v) {
        return Math.sqrt(Math.pow(getCoords().getKey() - v.getCoords().getKey(), 2)
                + Math.pow(getCoords().getValue() - v.getCoords().getValue(), 2));
    }
}

