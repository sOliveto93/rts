package main.java.com;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TileSheet {
    Tile[][] tileSheet;

    int[][] mapa = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 1, 1, 0, 4, 4, 1, 1, 0, 0, 0, 1, 1, 0, 4, 4, 1, 1, 0, 0, 0, 1, 1, 0, 4, 4, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 1, 1, 0, 3, 3, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1}
};
    BufferedImage spriteSheet1;

    public TileSheet() {
        tileSheet = new Tile[mapa[0].length][mapa.length];
        loadSpriteSheet();
        init();
    }

    //solo para mapas simetricos 10*10 20*20 etc..por la inversion de ejes
    public void init() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                //invertimos los ejes por j(filas) i (columnas) para que respete el diseño de la matriz
                if (mapa[i][j] == 0) {
                    tileSheet[j][i] = new Tile(getTileImage(0), false); // Casillas libres
                } else if (mapa[i][j] == 1) {
                    tileSheet[j][i] = new Tile(getTileImage(4), true); // Obstáculos
                } else if (mapa[i][j] == 3) {
                    tileSheet[j][i] = new Tile(getTileImage(3), true); // Obstáculos
                }
                else if (mapa[i][j] == 4) {
                    tileSheet[j][i] = new Tile(getTileImage(1), true); // Obstáculos
                }
            }
        }
        System.out.println("ancho alto sprite sheet" + mapa.length + "--" + mapa[0].length);
    }

    public void loadSpriteSheet() {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("img/tileSheet.png");
            if (input != null) {
                spriteSheet1 = ImageIO.read(input);
            } else {
                System.out.println("no se encontro el recurso");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Image getTileImage(int n) {
        int tileSize = 64;
        int spriteSheetWidth = spriteSheet1.getWidth();
        int spriteSheetHeight = spriteSheet1.getHeight();

        int cols = spriteSheetWidth / tileSize;
        int rows = spriteSheetHeight / tileSize;

        if (n < 0 || n >= rows * cols) {
            throw new IllegalArgumentException("Índice fuera de rango.");
        }

        int row = n / rows;
        int col = n % cols;

        return spriteSheet1.getSubimage(col * tileSize, row * tileSize, tileSize, tileSize);
    }

    public Tile[][] getTileSheet() {
        return tileSheet;
    }

    public void setTileSheet(Tile[][] tileSheet) {
        this.tileSheet = tileSheet;
    }

}
