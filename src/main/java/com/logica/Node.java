package main.java.com.logica;
import java.util.Objects;

public class Node {
    int x;
    int y;
    double gCost, hCost, fCost;
    Node parent;
    private static final int TILE_SIZE = 64;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.gCost = Double.MAX_VALUE;
        this.hCost = 0;
        this.fCost = Double.MAX_VALUE;
        this.parent = null;
    }

    public int getPixelX() {
        return x * TILE_SIZE + TILE_SIZE / 2; // Centrado en el tile
    }

    // Obtener la posición Y en píxeles en el mapa (no la coordenada del grid)
    public int getPixelY() {
        return y * TILE_SIZE + TILE_SIZE / 2; // Centrado en el tile
    }

    // Método para obtener la distancia entre dos nodos en el grid (usado para A* o cualquier otro algoritmo)
    public static double calculateDistance(Node node1, Node node2) {
        return Math.sqrt(Math.pow(node2.x - node1.x, 2) + Math.pow(node2.y - node1.y, 2));
    }

    // Método para obtener una clave única para el nodo (en formato "x,y")
    public String getKey() {
        return x + "," + y; // Esto crea una cadena única como "x,y" que se puede usar como clave
    }

    // Sobrescribir equals y hashCode para que funcione correctamente en HashMap y HashSet
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getgCost() {
        return gCost;
    }

    public void setgCost(double gCost) {
        this.gCost = gCost;
    }

    public double gethCost() {
        return hCost;
    }

    public void sethCost(double hCost) {
        this.hCost = hCost;
    }

    public double getfCost() {
        return fCost;
    }

    public void setfCost(double fCost) {
        this.fCost = fCost;
    }
}
