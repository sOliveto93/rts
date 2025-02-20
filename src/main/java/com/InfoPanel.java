package main.java.com;

import main.java.com.entidad.Edificio;
import main.java.com.entidad.Entidad;
import main.java.com.entidad.Unidad;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.beans.Beans.isInstanceOf;

public class InfoPanel {

    private List<Unidad> unidades = new ArrayList<>();
    private Unidad unidad;
    private Edificio edificio;
    int x = 0, y, w, h = 150;
    boolean isVisible = false;
    boolean mostrarUnidad = false;
    boolean mostrarEjercito = false;
    boolean mostrarEdificio = false;

    public InfoPanel(int w, int h) {
        this.w = w;
        this.y = h - this.h;
    }

    public void paint(Graphics g, Camara camara) {
        if (!isVisible()) {
            return;
        }
        //sumamos para fijarlo que en mapa necesitamos moverlo
        g.translate(+camara.getX(), +camara.getY());

        g.setColor(new Color(0, 0, 0, 90));
        g.fillRect(0, y, w, h);
        g.setColor(Color.white);
        if (mostrarEdificio) {
            g.drawString(edificio.getNombre(), getX(), getY() + 50);
            g.drawImage(edificio.getImagen(), getX(), getY() + 60, 50, 50, null);
        }
        if (mostrarUnidad) {
            g.drawString(unidad.getNombre(), getX(), getY() + 20);
            g.drawImage(unidad.getImagen(), getX(), getY() + 60, 50, 50, null);
            g.drawString(unidad.getNombre(), getX()+150, getY() + 20);
            g.drawString("Ataque: "+unidad.getAtaque(), getX()+150, getY() + 40);
            g.drawString("Defensa: "+unidad.getDefensa(), getX()+150, getY() + 60);
            g.drawString("Velocidad: "+unidad.getVelocidad(), getX()+150, getY() + 80);
        }
        if (mostrarEjercito) {
            int i=0;
            int j=20;
            int gap=20;
            for(Unidad u:unidades){

                g.drawRect(getX()+i,getY(),150,100);
                g.drawString(unidad.getNombre(), getX()+i, getY()+j);
                g.drawImage(unidad.getImagen(), getX()+i, getY() +j+20, 50, 50, null);
                i+=150;
            }
        }

    }


    public void mostrarInformacion(List<Entidad> entidades) {
        mostrarEdificio = false;
        mostrarEjercito = false;
        mostrarUnidad = false;
        setVisible(true);
        if (entidades.isEmpty() || entidades == null) {
            return;
        }
        if (entidades.size() > 1) {
            System.out.println("son un ejercito");

            for (Entidad e : entidades) {
                unidad = (Unidad) e;
                unidades.add(unidad);
                System.out.println("-----------------");
                System.out.println("info de la unidad");
                System.out.println(unidad.getNombre() + " --vida " + unidad.getVida() + " --ataque " + unidad.getAtaque() + " --defensa " + unidad.getDefensa() + " --velocidad " + unidad.getVelocidad());
                System.out.println("----------------");
                e.getNombre();
            }
            mostrarEjercito = true;
        } else {
            Entidad entidad = entidades.getFirst();
            boolean esEdificio = isInstanceOf(entidad, Edificio.class);
            if (esEdificio) {
                edificio = (Edificio) entidad;
                System.out.println("-----------------");
                System.out.println("info del edificio");
                System.out.println(edificio.getNombre() + " -- " + edificio.getX() + " -- " + edificio.getY());
                System.out.println("----------------");
                entidad.getNombre();
                mostrarEdificio = true;
            } else {
                unidad = (Unidad) entidad;
                System.out.println("-----------------");
                System.out.println("info de la unidad");
                System.out.println(unidad.getNombre() + " --vida " + unidad.getVida() + " --ataque " + unidad.getAtaque() + " --defensa " + unidad.getDefensa() + " --velocidad " + unidad.getVelocidad());
                System.out.println("----------------");
                entidad.getNombre();
                mostrarUnidad = true;
            }
        }

    }


    public void ocultarMenu() {
        if (unidad != null) {
            this.unidad = null;
        }
        if (edificio != null) {
            this.edificio = null;
        }
        if (unidades != null || !unidades.isEmpty()) {
            this.unidades.clear();
        }


        setVisible(false);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
