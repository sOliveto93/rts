package main;

import java.awt.*;
import java.util.List;

public class Unidad {
    private String nombre;
    private int x;
    private int y;
    private int velocidad;
    private int vida;
    private int ataque;
    private int defensa;
    private int size = 30;
    private boolean isSelected = false;
    private int targetX;
    private int targetY;
    private boolean moviendo = false;
    private boolean pathFindingInProgress = false;
    private Node objetivo;
    private List<Node> path;
    private int currentTargetIndex = 0;
    private long lastMoveTime = 0;
    public Unidad(String nombre, int x, int y, int velocidad, int vida, int ataque, int defensa) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
    }

    public void paint(Graphics g) {
        if (isSelected) {
            g.setColor(Color.CYAN);
            g.drawRect(x, y, size, size);
        } else {
            g.setColor(Color.MAGENTA);
        }
        g.fillRect(x, y, size, size);
    }

    public void update() {
        if (moviendo) {
            move();
        }
    }


    public boolean clickaUnidad(int mouseX, int mouseY) {
        return mouseX >= x
                && mouseX <= x + size
                && mouseY >= y
                && mouseY <= y + size;
    }
    // Añadir una constante de tolerancia de llegada
    private static final double TOLERANCIA_ARRIBO = 2.0; // Ajusta este valor según lo que consideres apropiado

//    public void move() {
//        if (path != null && !path.isEmpty()) {
//            Node siguienteNodo = path.get(0);  // El primer nodo en el camino
//
//            // Desplazar la unidad hacia el siguiente nodo
//            if (x < siguienteNodo.x * 64) {
//                x++;  // Mover hacia la derecha
//            } else if (x > siguienteNodo.x * 64) {
//                x--;  // Mover hacia la izquierda
//            }
//
//            if (y < siguienteNodo.y * 64) {
//                y++;  // Mover hacia abajo
//            } else if (y > siguienteNodo.y * 64) {
//                y--;  // Mover hacia arriba
//            }
//
//            // Si la unidad llega al siguiente nodo, lo eliminamos de la lista de camino
//            if (x == siguienteNodo.x * 64 && y == siguienteNodo.y * 64) {
//                path.remove(0);  // Eliminar el nodo actual del camino
//            }
//
//            // Si no hay más nodos en el camino, detener el movimiento
//            if (path.isEmpty()) {
//                moviendo = false;
//            }
//        }
//    }
public void move() {
    if (path != null && !path.isEmpty()) {
        Node siguienteNodo = path.get(0);

        // Desplazar la unidad hacia el siguiente nodo
        if (x < siguienteNodo.x * 64) {
            x++;
        } else if (x > siguienteNodo.x * 64) {
            x--;
        }

        if (y < siguienteNodo.y * 64) {
            y++;
        } else if (y > siguienteNodo.y * 64) {
            y--;
        }

        // Si la unidad llega al siguiente nodo, lo eliminamos de la lista de camino
        if (Math.abs(x - siguienteNodo.x * 64) < 2 && Math.abs(y - siguienteNodo.y * 64) < 2) {
            path.remove(0);  // Eliminar el nodo actual del camino
        }

        // Si no hay más nodos en el camino, detener el movimiento
        if (path.isEmpty()) {
            moviendo = false;
        }
    }
}


    public List<Node> calcularPath(Tile[][] mapa) {
        AStarPathfinding aStar = new AStarPathfinding(mapa);
        Node startNode = new Node(this.x / 64, this.y / 64);//Convertir la posición de la unidad en nodos
        Node endNode = this.objetivo;
        return aStar.aStar(startNode, endNode);
    }

    //-------------------------------get y set------------------
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public boolean isMoviendo() {
        return moviendo;
    }

    public void setMoviendo(boolean moviendo) {
        this.moviendo = moviendo;
    }

    public Node getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Node objetivo, Tile[][] mapa) {
        int mapaWidth = mapa.length;  // Número de filas
        int mapaHeight = mapa[0].length;  // Número de columnas

        if (objetivo.x < 0 || objetivo.x >= mapaWidth || objetivo.y < 0 || objetivo.y >= mapaHeight) {
            System.out.println("Clic fuera de los límites del mapa");
            return;  // No hacer nada si el clic está fuera de los límites
        }
        //evitamos acumulacion de calculos
        if (!pathFindingInProgress) {
            this.objetivo = objetivo;
            //hilo separado para que no se sobrecargue
            PathFindingTask pathFindingTask = new PathFindingTask(this, objetivo, mapa);
            Thread pathFindingHilo = new Thread(pathFindingTask);
            pathFindingInProgress = true;  // Marcamos que el cálculo está en progreso
            pathFindingHilo.start();
        }
    }

    public List<Node> getPath() {
        return path;
    }

    public void setPath(List<Node> path) {
        this.path = path;
        pathFindingInProgress = false; // Marcamos que el cálculo ha terminado
    }
}
