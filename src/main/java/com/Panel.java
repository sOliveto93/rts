package main.java.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {
    private Mapa mapa;
    private List<Unidad> unidades;
    private float fps;
    private double ticks;
    Font font;

    //para la seleccion multiple
    private int startX, startY, endX, endY;
    private boolean isSelecting = false;
    //minimo de arrastre del click
    private static final int TOLERANCE = 5;

    public Panel(Game game) {
        this.mapa = game.getMapa();
        setPreferredSize(new Dimension(game.getWidth(), game.getHeight()));
        setBackground(Color.white);
        unidades = game.getUnidades();
        font = new Font("Arial", Font.BOLD, 20);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        mapa.paint(g);

        // Dibujar las unidades
        for (int i = 0; i < unidades.size(); i++) {
            unidades.get(i).paint(g);
        }

        // Dibujar el camino final (si existe)
        for (Unidad u : unidades) {
            if (u.isSelected() && u.isMoviendo()) {

                List<Node> path = u.getPath();
                if (path != null && path.size() > 0) { // Aseguramos que haya al menos un nodo en el camino
                    g.setColor(Color.black); // Color para el camino final

                    // Obtenemos la posición de la unidad (su ubicación actual)
                    int startX = u.getX() / 64;  // La posición en el grid (en unidades de tile)
                    int startY = u.getY() / 64;

                    // Primero, dibujamos una línea desde la posición actual de la unidad al primer nodo del camino
                    Node startNode = path.get(0); // El primer nodo del camino
                    int x1 = startX * 64 + 32;
                    int y1 = startY * 64 + 32;
                    int x2 = startNode.x * 64 + 32;
                    int y2 = startNode.y * 64 + 32;
                    g.drawLine(x1, y1, x2, y2);

                    // Luego, dibujamos las líneas entre los nodos del camino
                    for (int i = 0; i < path.size() - 1; i++) {
                        Node node1 = path.get(i);
                        Node node2 = path.get(i + 1);

                        int xStart = node1.x * 64 + 32;
                        int yStart = node1.y * 64 + 32;
                        int xEnd = node2.x * 64 + 32;
                        int yEnd = node2.y * 64 + 32;

                        g.drawLine(xStart, yStart, xEnd, yEnd);
                    }
                }
            }
        }

        //rect de seleccion
        if (isSelecting) {
            g.setColor(new Color(0, 0, 255, 50));
            g.fillRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
            g.setColor(Color.BLUE);
            g.drawRect(Math.min(startX, endX), Math.min(startY, endY),
                    Math.abs(endX - startX), Math.abs(endY - startY));
        }


        // Mostrar información adicional (FPS, ticks)
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("FPS: " + fps, 10, 20);
        g.drawString("Ticks: " + ticks, 10, 40);
    }


    public float getFps() {
        return fps;
    }

    public void setFps(float fps) {
        this.fps = fps;
    }

    public double getTicks() {
        return ticks;
    }

    public void setTicks(double ticks) {
        this.ticks = ticks;
    }

//------------------------------eventos de click


    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {  // clic izquierdo
            startX = e.getX();
            startY = e.getY();
             // Empezamos el proceso de selección

            // Si no se hizo clic en ninguna unidad, deseleccionamos todas
            for (Unidad u : unidades) {
                u.setSelected(false);
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {  // clic derecho
            for (Unidad u : unidades) {
                if (u.isSelected()) {
                    // Obtener coordenadas para el destino y mover la unidad
                    int targetX = e.getX() / 64;
                    int targetY = e.getY() / 64;
                    Tile targetTile = mapa.getTileSheet()[targetX][targetY];
                    if (!targetTile.isObstaculo()) {
                        Node objetivo = new Node(targetX, targetY);
                        List<Node> path = new AStarPathfinding(mapa.getTileSheet()).aStar(
                                new Node(u.getX() / 64, u.getY() / 64), objetivo
                        );
                        if (path != null) {
                            u.setPath(path);  // Asignamos el camino encontrado
                            u.setMoviendo(true);  // Iniciamos el movimiento
                        }
                    } else {
                        System.out.println("El objetivo es un obstáculo, no se puede mover.");
                        u.setMoviendo(false);
                    }
                }
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1) {  // clic izquierdo
            endX = e.getX();
            endY = e.getY();
            isSelecting = false;  // Terminamos la selección

            // Verificamos si el movimiento fue un arrastre o un clic simple
            if (Math.abs(startX - endX) < TOLERANCE && Math.abs(startY - endY) < TOLERANCE) {
                // Es un clic simple: seleccionar o deseleccionar una unidad
                for (Unidad u : unidades) {
                    if (u.clickaUnidad(startX, startY)) {
                        u.setSelected(!u.isSelected());  // Alternar selección
                    }
                }
            } else {
                // Es un arrastre: seleccionamos unidades dentro del área
                Rectangle selectionRect = new Rectangle(Math.min(startX, endX), Math.min(startY, endY),
                        Math.abs(endX - startX), Math.abs(endY - startY));

                for (Unidad u : unidades) {
                    if (selectionRect.contains(u.getX(), u.getY())) {
                        u.setSelected(true);  // Seleccionamos la unidad
                    } else {
                        u.setSelected(false);  // Deseleccionamos
                    }
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        isSelecting = true;
        if (isSelecting) {
            endX = e.getX();
            endY = e.getY();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
