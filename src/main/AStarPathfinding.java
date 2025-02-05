package main;

import java.util.*;

public class AStarPathfinding {
    private Tile[][] mapa;

    public AStarPathfinding(Tile[][] mapa) {
        this.mapa = mapa;

    }

    public List<Node> aStar(Node start, Node end) {
        if (mapa[end.x][end.y].isObstaculo()) {
            return null;  // Si el objetivo es un obstáculo, no hay camino posible
        }


        // Inicializamos las listas para cada nueva ejecución de A*
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fCost));
        Set<Node> closedList = new HashSet<>();

        // Inicializamos los costos del nodo de inicio
        start.gCost = 0;
        start.hCost = calularHeuristica(start, end);
        start.fCost = start.gCost + start.hCost;
        openList.add(start);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            closedList.add(currentNode);

            if (currentNode.x == end.x && currentNode.y == end.y) {
                // Llegamos al objetivo, reconstruir el camino
                List<Node> path = new ArrayList<>();
                Node current = currentNode;
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;  // Retornamos el camino encontrado
            }

            List<Node> vecinos = getVecinos(currentNode);

            for (Node vecino : vecinos) {
                if (closedList.contains(vecino)) {
                    continue;  // Si el vecino ya ha sido procesado, lo saltamos
                }

                double tentativeGCost = currentNode.gCost + 10;  // Suponemos que el costo de moverse a un vecino es 1

                // Si encontramos un mejor camino hacia el vecino, lo actualizamos
                if (tentativeGCost < vecino.gCost) {
                    vecino.parent = currentNode;
                    vecino.gCost = tentativeGCost;
                    vecino.hCost = calularHeuristica(vecino, end);
                    vecino.fCost = vecino.gCost + vecino.hCost;

                    if (!openList.contains(vecino)) {
                        openList.add(vecino);
                    }
                }
            }
        }
        return null;  // Si no encontramos un camino
    }


    public double calularHeuristica(Node start, Node end) {
        return Math.abs(start.x - end.x) + Math.abs(start.y - end.y);//distancia manhatan
    }
    public List<Node> getVecinos(Node node) {
        List<Node> vecinos = new ArrayList<>();

        // Direcciones cardinales y diagonales
        int[] dx = {-1, 1, 0, 0, -1, 1, -1, 1};  // Cambios en la coordenada x
        int[] dy = {0, 0, -1, 1, -1, -1, 1, 1};  // Cambios en la coordenada y
        //reemplazar por 8 para contemplar las diagonales
        for (int i = 0; i < 4; i++) {
            int newX = node.x + dx[i];
            int newY = node.y + dy[i];

            // Verificar si la nueva posición está dentro de los límites del mapa
            if (newX >= 0 && newX < mapa.length && newY >= 0 && newY < mapa[0].length) {
                // Verificar si el tile no es un obstáculo
                if (!mapa[newX][newY].isObstaculo()) {

                    vecinos.add(new Node(newX, newY));
                }
            }
        }
        return vecinos;
    }


}
