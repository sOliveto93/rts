package main.java.com.entidad;

import main.java.com.Tile;
import main.java.com.logica.Node;
import main.java.com.logica.PathFindingTask;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Unidad extends Entidad {
    private String nombre;
    private int velocidad;
    private int vida;
    private int ataque;
    private int defensa;
    private boolean isSelected = false;
    private int targetX;
    private int targetY;
    private boolean moviendo = false;
    private boolean pathFindingInProgress = false;
    private Node objetivo;
    private List<Node> pathNodes;
    private int indexSrpiteIdle = 0;
    //variables para manejar la velocidad de actualizacion de dibujado
    private long lastSpriteUpdate = 0;
    private final long intervaloActualizacion = 100;

    public Unidad(String nombre, int x, int y, int velocidad, int vida, int ataque, int defensa) {

        super(nombre, x, y, "img/idle.png");
        this.nombre = nombre;


        this.velocidad = velocidad;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;

    }

    public void paint(Graphics g) {
        if (isSelected) {
            g.setColor(Color.CYAN);
            g.drawRect(getX(), getY(), size, size);
        }

        g.drawImage(super.getTileImage(indexSrpiteIdle), getX(), getY(), null);


    }

    public void update() {
        if (moviendo) {
            move();
        }
        long curretTime = System.currentTimeMillis();
        if (curretTime - lastSpriteUpdate > intervaloActualizacion) {
            indexSrpiteIdle++;
            if (indexSrpiteIdle > 2) {//cambiar esto para que sea variable por la hoja de sprites
                indexSrpiteIdle = 0;
            }
            lastSpriteUpdate = curretTime;
        }

    }

    public boolean clickaUnidad(int mouseX, int mouseY) {
        return mouseX >= getX()
                && mouseX <= getX() + size
                && mouseY >= getY()
                && mouseY <= getY() + size;
    }

    // Añadir una constante de tolerancia de llegada
    private static final double TOLERANCIA_ARRIBO = 2.0; // Ajusta este valor según lo que consideres apropiado


    public void move() {
        if (pathNodes != null && !pathNodes.isEmpty()) {
            Node siguienteNodo = pathNodes.getFirst();
            if (siguienteNodo != null) {
                //centramos el nodo a la celda
                int targeXPos = siguienteNodo.getX() * 64 + (64 - size) / 2;
                int targeYPos = siguienteNodo.getY() * 64 + (64 - size) / 2;

                // Desplazar la unidad hacia el siguiente nodo
                if (getX() < targeXPos) {
                    int current = getX();
                    current++;
                    setX(current);
                } else if (getX() > targeXPos) {
                    int current = getX();
                    current--;
                    setX(current);
                }

                if (getY() < targeYPos) {
                    int current = getY();
                    current++;
                    setY(current);

                } else if (getY() > targeYPos) {
                    int current = getY();
                    current--;
                    setY(current);

                }

                // Si la unidad llega al siguiente nodo, lo eliminamos de la lista de camino
                if (Math.abs(getX() - targeXPos) < TOLERANCIA_ARRIBO && Math.abs(getY() - targeYPos) < TOLERANCIA_ARRIBO) {

                    pathNodes.removeFirst();  // Eliminar el nodo actual del camino


                }

                // Si no hay más nodos en el camino, detener el movimiento
                if (pathNodes.isEmpty()) {
                    moviendo = false;
                }
            }
        }
    }


    //-------------------------------get y set------------------
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

        if (objetivo.getX() < 0 || objetivo.getX() >= mapaWidth || objetivo.getY() < 0 || objetivo.getY() >= mapaHeight) {
            System.out.println("Clic fuera de los límites del mapa");
            return;  // No hacer nada si el clic está fuera de los límites
        }
        //evitamos acumulacion de calculos
        if (!pathFindingInProgress) {
            this.objetivo = objetivo;
            //hilo separado para que no se sobrecargue             unidad, objetivo, tileMap[][]
            PathFindingTask pathFindingTask = new PathFindingTask(this, objetivo, mapa);
            Thread pathFindingHilo = new Thread(pathFindingTask);
            pathFindingInProgress = true;  // Marcamos que el cálculo está en progreso
            pathFindingHilo.start();
        }
    }


    public List<Node> getPathNodes() {
        return pathNodes;
    }

    public void setPathNodes(List<Node> pathNodes) {
        this.pathNodes = pathNodes;
        pathFindingInProgress = false; // Marcamos que el cálculo ha terminado
    }

}
