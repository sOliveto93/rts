package main.java.com;

import main.java.com.entidad.Unidad;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Game extends JFrame implements Runnable {

    int fpsTarget = 60;
    long nanoSeg = 1_000_000_000;
    Mapa mapa;
    List<Unidad> unidades = new ArrayList<>();

    Panel panel;
    Thread hiloGame;

    public Game() {
        mapa = new Mapa(new TileSheet());
        unidades.add(new Unidad("soldado raso", (5 * 64)+32, (5 * 64)+32, 2, 100, 5, 5));
        unidades.add(new Unidad("soldado raso", (4 * 64)+32, (4 * 64)+32, 2, 100, 5, 5));
        unidades.add(new Unidad("soldado raso", (6 * 64)+32, (2 * 64)+32, 4, 100, 5, 5));

        panel=new Panel(this);
        start();
    }

    public void start() {
        this.setTitle("Juego rpg");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(640, 640);
        this.add(panel);
        this.setVisible(true);

        hiloGame = new Thread(this);
        hiloGame.start();

    }


    public List<Unidad> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidad> unidades) {
        this.unidades = unidades;
    }


    //----------------------bucle principal
    @Override
    public void run() {

        long lastTime = System.nanoTime();
        double nsPorTick = nanoSeg / fpsTarget;//16.6.....ms
        double delta = 0;
        int frames = 0;
        int ticks = 0;

        long timer = System.currentTimeMillis();

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPorTick; // si da =1 o >1 es tiempo de actualizar
            lastTime = now;

            boolean debeRenderizar = false;

            while (delta >= 1) {
                ticks++;
                //actualizar logica
                update();
                delta--;
                debeRenderizar = true;
            }

            if(debeRenderizar){
                frames++;
                //actualizamos la pantalla
                this.paint();
            }
            if(System.currentTimeMillis() - timer >= 1000){
                timer+=1000;
                System.out.println("FPS: "+frames+ "Ticks: "+ticks);
                panel.setFps(frames);
                panel.setTicks(ticks);
                frames=0;
                ticks=0;
            }
            try {
                Thread.sleep(5);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    public void paint(){
    panel.repaint();
    }
    public void update() {
       for(Unidad u : unidades){
           u.update();
       }
    }

    public int getFpsTarget() {
        return fpsTarget;
    }

    public void setFpsTarget(int fpsTarget) {
        this.fpsTarget = fpsTarget;
    }

    public long getNanoSeg() {
        return nanoSeg;
    }

    public void setNanoSeg(long nanoSeg) {
        this.nanoSeg = nanoSeg;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }
}
