package main.java.com.entidad;

import main.java.com.Mapa;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Edificio extends Entidad {

    Mapa mapa;
    int celdaX;
    int celdaY;
    Image imagenEdificio;


    public Edificio(String nombre, Mapa mapa, int x, int y) {
        //x e y son traducidas a escala del mapa
        super(nombre, x, y, "img/builds.png",64);
        celdaX=x;
        celdaY=y;
        this.mapa = mapa;
        imagenEdificio=getTileImage(0);

//seteamos esa casilla como obstaculo
        mapa.getTileSheet()[celdaX][celdaY].setObstaculo(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(imagenEdificio, getX() , getY() , null);

    }

    public void resetCasilla() {
        boolean currentState = mapa.getTileSheet()[5][2].isObstaculo();
        mapa.getTileSheet()[5][2].setObstaculo(!currentState);
    }

    public boolean contains(int x, int y) {
        int edificioX = getX() * size;
        int edificioY = getY() * size;

        return (x >= edificioX && x <= edificioX + size) && (y >= edificioY && y <= edificioY + size);
    }

    public Image getImagen() {
        return imagenEdificio;
    }

    public void setImagen(Image imagen) {
        this.imagenEdificio = imagen;
    }

    public int getCeldaX() {
        return celdaX;
    }

    public void setCeldaX(int celdaX) {
        this.celdaX = celdaX;
    }

    public int getCeldaY() {
        return celdaY;
    }

    public void setCeldaY(int celdaY) {
        this.celdaY = celdaY;
    }
}
