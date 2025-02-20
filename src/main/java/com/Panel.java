package main.java.com;

import main.java.com.entidad.Edificio;
import main.java.com.entidad.Entidad;
import main.java.com.entidad.Unidad;
import main.java.com.logica.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private int ancho,alto;
    private Camara camara;
    private Mapa mapa;
    private List<Unidad> unidades = new ArrayList<>();
    List<Edificio> edificios = new ArrayList<>();

    private float fps;
    private double ticks;
    Font font;

    //para la seleccion multiple
    private int startX, startY, endX, endY;
    private boolean isSelecting = false;
    //minimo de arrastre del click
    private static final int TOLERANCE = 5;

//panel de informacion
    InfoPanel infoPanel;

    public Panel(Mapa mapa,int ancho,int alto) {
        this.ancho=ancho;
        this.alto=alto;
        this.mapa = mapa;
        setPreferredSize(new Dimension(ancho, alto));
        setBackground(Color.white);
        edificios.add(new Edificio("castillo", mapa, 5, 2));
        unidades.add(new Unidad("soldado raso", 5 , 5 , 2, 100, 5, 5));
        unidades.add(new Unidad("soldado raso", 4, 4  , 2, 100, 5, 5));
        unidades.add(new Unidad("soldado raso", 6 , 2 , 4, 100, 5, 5));
        unidades.add(new Unidad("soldado raso", 6 , 3 , 4, 100, 5, 5));

        font = new Font("Arial", Font.BOLD, 20);
        camara = new Camara(ancho, alto);
        infoPanel=new InfoPanel(ancho,alto);


        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
    }

    public void update() {
        for (Unidad u : unidades) {
            u.update();
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        mapa.paint(g, camara);

        for (Edificio edificio : edificios) {
            edificio.paint(g);
        }

        // Dibujar las unidades
        for (Unidad unidad : unidades) {
            unidad.paint(g);
        }

        // Dibujar el camino final (si existe)
        for (Unidad u : unidades) {
            if (u.isSelected() && u.isMoviendo()) {

                List<Node> path = u.getPathNodes();
                if (path != null && !path.isEmpty()) {
                    g.setColor(Color.black);

                    // Obtenemos la posición de la unidad (su ubicación actual)
                    int startX = u.getX() / 64;  // La posición en el grid (en unidades de tile)
                    int startY = u.getY() / 64;

                    // Primero, dibujamos una línea desde la posición actual de la unidad al primer nodo del camino
                    Node startNode = path.getFirst(); // El primer nodo del camino
                    int x1 = startX * 64 + 32;
                    int y1 = startY * 64 + 32;
                    int x2 = startNode.getX() * 64 + 32;
                    int y2 = startNode.getY() * 64 + 32;
                    g.drawLine(x1, y1, x2, y2);

                    // Luego, dibujamos las líneas entre los nodos del camino
                    for (int i = 0; i < path.size() - 1; i++) {
                        Node node1 = path.get(i);
                        Node node2 = path.get(i + 1);

                        int xStart = node1.getX() * 64 + 32;
                        int yStart = node1.getY() * 64 + 32;
                        int xEnd = node2.getX() * 64 + 32;
                        int yEnd = node2.getY() * 64 + 32;

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
        infoPanel.paint(g,camara);
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

    public List<Unidad> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidad> unidades) {
        this.unidades = unidades;
    }

//------------------------------eventos de click


    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {  // clic izquierdo
            startX = e.getX() + camara.getX();
            startY = e.getY() + camara.getY();
            // Empezamos el proceso de selección
            infoPanel.ocultarMenu();
            // Si no se hizo clic en ninguna unidad, deseleccionamos todas
            for (Unidad u : unidades) {
                u.setSelected(false);
            }
            for(Edificio ed:edificios){
                ed.setSelected(false);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {  // clic derecho
            for (Unidad u : unidades) {
                if (u.isSelected()) {
                    // Obtener coordenadas para el destino y mover la unidad
                    int targetX = (e.getX() + camara.getX()) / 64;
                    int targetY = (e.getY() + camara.getY()) / 64;
                    Tile targetTile = mapa.getTileSheet()[targetX][targetY];

                    if (!targetTile.isObstaculo()) {

                        Node objetivo = new Node(targetX, targetY);
                        u.setObjetivo(objetivo, mapa.getTileSheet());

                    } else {
                        System.out.println("El objetivo es un obstáculo, no se puede mover.");
                        u.setMoviendo(false);
                    }
                }
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1) {  // clic izquierdo
            // Ajustar las coordenadas del clic con el desplazamiento de la cámara
            int adjustedStartX = startX;
            int adjustedStartY = startY;
            int adjustedEndX = e.getX() + camara.getX();
            int adjustedEndY = e.getY() + camara.getY();

            isSelecting = false;  // Terminamos la selección


            // Verificamos si el movimiento fue un arrastre o un clic simple
            if (Math.abs(adjustedStartX - adjustedEndX) < TOLERANCE && Math.abs(adjustedStartY - adjustedEndY) < TOLERANCE) {
                // Es un clic simple: seleccionar o deseleccionar una unidad
                for (Unidad u : unidades) {
                    if (u.clickaEntidad(adjustedStartX, adjustedStartY)) {
                        u.setSelected(!u.isSelected());
                       //mostrar info
                        List<Entidad> entidad=new ArrayList<>();
                        entidad.add(u);
                        infoPanel.mostrarInformacion(entidad);
                    }
                }
                for(Edificio ed:edificios){
                    if (ed.clickaEntidad(adjustedStartX, adjustedStartY)) {
                        ed.setSelected(!ed.isSelected());
                       //mostrarinfo
                        List<Entidad> entidad=new ArrayList<>();
                        entidad.add(ed);
                        infoPanel.mostrarInformacion(entidad);
                    }
                }
            } else {
                // Es un arrastre: seleccionamos unidades dentro del área
                Rectangle selectionRect = new Rectangle(Math.min(adjustedStartX, adjustedEndX), Math.min(adjustedStartY, adjustedEndY),
                        Math.abs(adjustedEndX - adjustedStartX), Math.abs(adjustedEndY - adjustedStartY));
                List<Entidad> entidades=new ArrayList<>();
                for (Unidad u : unidades) {
                    if (selectionRect.contains(u.getX(), u.getY())) {
                        u.setSelected(true);
                       //mostrar info

                        entidades.add(u);


                    } else {
                        u.setSelected(false);

                    }
                } infoPanel.mostrarInformacion(entidades);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        isSelecting = true; // esto siempre da true.. es solo pa que se muestre el rect de seleccion ARREGLAR
        if (isSelecting) {
            // Ajustar las coordenadas de arrastre con el desplazamiento de la cámara
            endX = e.getX() + camara.getX();
            endY = e.getY() + camara.getY();
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

    // evento teclado

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int moveSpeed = 10;  // La velocidad de desplazamiento de la cámara
        if (e.getKeyCode() == KeyEvent.VK_W) {
            camara.mover(0, -moveSpeed, mapa.getTileSheet().length * 64, mapa.getTileSheet()[0].length * 64); // Mover hacia arriba
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            camara.mover(0, moveSpeed, mapa.getTileSheet().length * 64, mapa.getTileSheet()[0].length * 64); // Mover hacia abajo
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            camara.mover(-moveSpeed, 0, mapa.getTileSheet().length * 64, mapa.getTileSheet()[0].length * 64); // Mover hacia la izquierda
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            camara.mover(moveSpeed, 0, mapa.getTileSheet().length * 64, mapa.getTileSheet()[0].length * 64); // Mover hacia la derecha
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            if (!edificios.isEmpty()) {
                edificios.getFirst().resetCasilla();
                edificios.removeFirst();
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
