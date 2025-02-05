package main;

import java.awt.*;

public class Tile {
    private int size=64;
    private Color background=Color.BLUE;
    private boolean isObstaculo=false;


    public Tile(Color color,boolean isObstaculo){
        this.background=color;
        this.isObstaculo=isObstaculo;

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


}
