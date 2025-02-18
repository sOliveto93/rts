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

    public void mover(int dx, int dy, int mapaWidth, int mapaHeight) {
        this.x += dx;
        this.y += dy;

        // Limitar el movimiento de la cámara dentro de los límites del mapa
        if (this.x < 0) {
            this.x = 0;
        }
        if (this.y < 0) {
            this.y = 0;
        }
        if (this.x > mapaWidth - ancho) {
            this.x = mapaWidth - ancho;
        }
        if (this.y > mapaHeight - alto) {
            this.y = mapaHeight - alto;
        }
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
