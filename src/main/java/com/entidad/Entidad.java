package main.java.com.entidad;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Entidad {
    private String nombre;
    private int x;
    private int y;
    String path;
    BufferedImage bufferSheet;
    int size=64;

    public Entidad(String nombre, int x, int y, String path) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.path = path;

        loadSpriteSheet(getPath());

    }

    public void loadSpriteSheet(String path) {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(path);
            if (input != null) {
                bufferSheet = ImageIO.read(input);

            } else {
                System.out.println("no se encontro el recurso");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Image getTileImage(int n) {
        int spriteSheetWidth = bufferSheet.getWidth();

        int cols = spriteSheetWidth / size;

        return bufferSheet.getSubimage(n * size, 0, size, size);
    }


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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public BufferedImage getBufferSheet() {
        return bufferSheet;
    }

    public void setBufferSheet(BufferedImage bufferSheet) {
        this.bufferSheet = bufferSheet;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
