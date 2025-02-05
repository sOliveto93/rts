package main;

public class Node {
    int x;
    int y;
    double gCost,hCost,fCost;
    Node parent;
    private static final int TILE_SIZE=64;
    public Node(int x,int y){
        this.x=x;
        this.y=y;
        this.gCost=Double.MAX_VALUE;
        this.hCost=0;
        this.fCost=Double.MAX_VALUE;
        this.parent=null;
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
}
