package main;

import java.util.List;

public class PathFindingTask implements Runnable {
    private Unidad unidad;
    private Node objetivo;
    private Tile[][] mapa;

    public PathFindingTask(Unidad unidad, Node objetivo, Tile[][] mapa) {
        this.unidad = unidad;
        this.objetivo = objetivo;
        this.mapa = mapa;
    }

    @Override
    public void run() {
        List<Node> path = new AStarPathfinding(mapa).aStar(new Node(unidad.getX() / 64, unidad.getY() / 64), objetivo);

        if (path != null && !path.isEmpty()) {
            unidad.setPath(path);
            unidad.setMoviendo(true);
        } else {
            unidad.setMoviendo(false);
        }
    }
}
