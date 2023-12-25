package fr.projet.Graph;

import javafx.util.Pair;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Accessors(chain = true)
@Data
public class Vertex {
    private List<Vertex> listNeighbors;
    private List<Integer> listNeighborsCut = new ArrayList<>();
    private List<Integer> listNeighborsPaint = new ArrayList<>();
    private Pair<Integer, Integer> coords;

    public Vertex(ArrayList<Vertex> vertices, int x, int y) {
        this.listNeighbors = vertices;
        this.coords = new Pair<>(x, y);
    }

    public Vertex(int x, int y) {
        this.listNeighbors = new ArrayList<>();
        this.coords = new Pair<>(x, y);
    }

    public void addNeighborVertex(Vertex v) {
        listNeighbors.add(v);
    }

    public void removeNeighborVertex(Vertex v) {
        listNeighbors.remove(v);
    }

    public void removeNeighborVertex(int i) {
        listNeighbors.remove(i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(coords, vertex.coords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coords);
    }

    @Override
    public String toString() {
        return "Vertex(" + coords.getKey() + ", " + coords.getValue() + ')';
    }

    public static boolean isSameCouple(Pair<Vertex, Vertex> vertex1, Pair<Vertex, Vertex> vertex2) {
        return (vertex1.getKey() == vertex2.getKey() && vertex1.getValue() == vertex2.getValue()) ||
                (vertex1.getKey() == vertex2.getValue() && vertex1.getValue() == vertex2.getKey());
    }

    public void cut(Vertex v) {
        listNeighborsCut.add(listNeighbors.indexOf(v));
    }

    public boolean isCut(Vertex v) {
        return listNeighborsCut.contains(listNeighbors.indexOf(v));
    }

    public void paint(Vertex v) {
        listNeighborsPaint.add(listNeighbors.indexOf(v));
    }

    public boolean isPainted(Vertex v) {
        return listNeighborsPaint.contains(listNeighbors.indexOf(v));
    }

    public boolean isCutOrPanted(Vertex v) {
        return isCut(v) || isPainted(v);
    }
}
