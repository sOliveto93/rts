package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class Panel extends JPanel implements MouseListener {
    private Mapa mapa;
    private List<Unidad> unidades;
    private float fps;
    private double ticks;
    Font font;

    public Panel(Game game) {
        this.mapa = game.getMapa();
        setPreferredSize(new Dimension(game.getWidth(), game.getHeight()));
        setBackground(Color.white);
        unidades = game.getUnidades();
        font = new Font("Arial", Font.BOLD, 20);
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        mapa.paint(g);  // Dibuja el mapa (como ya lo haces)

        // Dibujar las unidades
        for (int i = 0; i < unidades.size(); i++) {
            unidades.get(i).paint(g);
        }

        // Dibujar el camino final (si existe)
        for(Unidad u:unidades) {
            if (u.isSelected() && u.isMoviendo()) {

                List<Node> path = u.getPath();
                if (path != null && path.size() > 0) { // Aseguramos que haya al menos un nodo en el camino
                    g.setColor(Color.black); // Color para el camino final

                    // Obtenemos la posición de la unidad (su ubicación actual)
                    int startX = u.getX() / 64;  // La posición en el grid (en unidades de tile)
                    int startY = u.getY() / 64;

                    // Primero, dibujamos una línea desde la posición actual de la unidad al primer nodo del camino
                    Node startNode = path.get(0); // El primer nodo del camino
                    int x1 = startX * 64 + 32;  // Centrado en el tile
                    int y1 = startY * 64 + 32;  // Centrado en el tile
                    int x2 = startNode.x * 64 + 32;    // Centrado en el tile del primer nodo
                    int y2 = startNode.y * 64 + 32;    // Centrado en el tile del primer nodo
                    g.drawLine(x1, y1, x2, y2);  // Línea desde la unidad hasta el primer nodo del camino

                    // Luego, dibujamos las líneas entre los nodos del camino
                    for (int i = 0; i < path.size() - 1; i++) {
                        Node node1 = path.get(i);
                        Node node2 = path.get(i + 1);

                        int xStart = node1.x * 64 + 32;  // Centrado en el tile
                        int yStart = node1.y * 64 + 32;  // Centrado en el tile
                        int xEnd = node2.x * 64 + 32;    // Centrado en el tile
                        int yEnd = node2.y * 64 + 32;    // Centrado en el tile

                        g.drawLine(xStart, yStart, xEnd, yEnd);  // Dibujar línea entre los nodos
                    }
                }
            }
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
        if (e.getButton() == MouseEvent.BUTTON1) {//click izquierdo

            for (Unidad u : unidades) {
                if (u.clickaUnidad(e.getX(), e.getY())) {
                    u.setSelected(true);
                } else {
                    u.setSelected(false);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) { // clic derecho
            for (Unidad u : unidades) {
                if (u.isSelected()) {
                    // Obtener las coordenadas donde el usuario hace clic
                    int targetX = e.getX() / 64;
                    int targetY = e.getY() / 64;

                    // Verificar si la casilla es un obstáculo
                    Tile targetTile = mapa.getTileSheet()[targetX][targetY];
                    if (targetTile.isObstaculo()) {
                        System.out.println("El objetivo es un obstáculo, no se puede mover.");
                        u.setMoviendo(false);  // Detener el movimiento si es un obstáculo
                    } else {
                        Node objetivo = new Node(targetX, targetY);
                        // Ejecutar A* para encontrar un camino hacia el objetivo
                        List<Node> path = new AStarPathfinding(mapa.getTileSheet()).aStar(
                                new Node(u.getX() / 64, u.getY() / 64), objetivo
                        );

                        if (path != null) {
                            u.setPath(path); // Establecer el camino a la unidad
                            u.setMoviendo(true); // Hacer que la unidad comience a moverse
                        } else {
                            System.out.println("No se pudo encontrar un camino.");
                            u.setMoviendo(false);  // Detener el movimiento si no se encuentra un camino
                        }
                    }
                }
            }
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
}
