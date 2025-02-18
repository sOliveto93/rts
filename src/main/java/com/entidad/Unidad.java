package main.java.com.entidad;

import main.java.com.Tile;
import main.java.com.logica.Node;
import main.java.com.logica.PathFindingTask;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Unidad {
    private String nombre;
    private int x;
    private int y;
    private int velocidad;
    private int vida;
    private int ataque;
    private int defensa;
    private int size = 64;
    private boolean isSelected = false;
    private int targetX;
    private int targetY;
    private boolean moviendo = false;
    private boolean pathFindingInProgress = false;
    private Node objetivo;
    private List<Node> path;
    BufferedImage spriteIdle;
    private int indexSrpiteIdle=0;
    //variables para manejar la velocidad de actualizacion de dibujado
    private long lastSpriteUpdate=0;
    private final long intervaloActualizacion=100;

    public Unidad(String nombre, int x, int y, int velocidad, int vida, int ataque, int defensa) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        loadSpriteSheet();
    }

    public void paint(Graphics g) {
        if (isSelected) {
            g.setColor(Color.CYAN);
            g.drawRect(x, y, size, size);
        }

            g.drawImage(getTileImage(indexSrpiteIdle),x,y,null);


    }

    public void update() {
        if (moviendo) {
            move();
        }
        long curretTime=System.currentTimeMillis();
        if(curretTime-lastSpriteUpdate>intervaloActualizacion){
            indexSrpiteIdle++;
            if(indexSrpiteIdle > 2){//cambiar esto para que sea variable por la hoja de sprites
                indexSrpiteIdle=0;
            }
            lastSpriteUpdate=curretTime;
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


public void move() {
    if (path != null && !path.isEmpty()) {
        Node siguienteNodo = path.getFirst();


        //centramos el nodo a la celda
        int targeXPos= siguienteNodo.getX()*64+(64-size)/2;
        int targeYPos= siguienteNodo.getY()*64+(64-size)/2;

        // Desplazar la unidad hacia el siguiente nodo
        if (x < targeXPos) {
            x++;
        } else if (x > targeXPos) {
            x--;
        }

        if (y < targeYPos) {
            y++;
        } else if (y > targeYPos) {
            y--;
        }

        // Si la unidad llega al siguiente nodo, lo eliminamos de la lista de camino
        if (Math.abs(x - targeXPos) < TOLERANCIA_ARRIBO && Math.abs(y - targeYPos) < TOLERANCIA_ARRIBO) {
            path.removeFirst();  // Eliminar el nodo actual del camino
        }

        // Si no hay más nodos en el camino, detener el movimiento
        if (path.isEmpty()) {
            moviendo = false;
        }
    }
}


    public void loadSpriteSheet() {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("img/idle.png");
            if (input != null) {
                spriteIdle = ImageIO.read(input);

            } else {
                System.out.println("no se encontro el recurso");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public Image getTileImage(int n) {
        int spriteSheetWidth = spriteIdle.getWidth();

        int cols = spriteSheetWidth / size;

        return spriteIdle.getSubimage(n * size, 0, size, size);
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

    public List<Node> getPath() {
        return path;
    }

    public void setPath(List<Node> path) {
        this.path = path;
        pathFindingInProgress = false; // Marcamos que el cálculo ha terminado
    }
}
