package main.java.com.logica;

import main.java.com.Tile;
import main.java.com.entidad.Unidad;

import java.util.List;

public class PathFindingTask implements Runnable {
    final private Unidad UNIDAD;
    final private Node OBJETIVO;
    final private Tile[][] MAPA;

    public PathFindingTask(Unidad unidad, Node objetivo, Tile[][] mapa) {
        this.UNIDAD = unidad;
        this.OBJETIVO = objetivo;
        this.MAPA = mapa;
    }

    @Override
    public void run() {
        List<Node> path = new AStarPathfinding(MAPA).aStar(new Node(UNIDAD.getX() / 64, UNIDAD.getY() / 64), OBJETIVO);

        if (path != null && !path.isEmpty()) {
            UNIDAD.setPath(path);
            UNIDAD.setMoviendo(true);
        } else {
            UNIDAD.setMoviendo(false);
        }
    }
}
