package main.java.com;

import main.java.com.logica.Node;

import java.awt.*;

public class Tile {
    private int size=64;
    private Color background=Color.BLUE;
    private boolean isObstaculo=false;
    private Image image;

    public Tile(Image image,boolean isObstaculo){
        this.image=image;
        this.isObstaculo=isObstaculo;

    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return false;
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public boolean isObstaculo() {
        return isObstaculo;
    }

    public void setObstaculo(boolean obstaculo) {
        isObstaculo = obstaculo;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
