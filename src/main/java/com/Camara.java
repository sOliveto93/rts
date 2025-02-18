package main.java.com;

public class Camara {
    private int x, y;
    private int ancho, alto;

    public Camara(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.x = 0;
        this.y = 0;
    }

    public void mover(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}
