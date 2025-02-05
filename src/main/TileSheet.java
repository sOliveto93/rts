package main;

import java.awt.*;

public class TileSheet {
    Tile[][] tileSheet;
    private int[][] mapa = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    };

    public TileSheet(int w, int h) {
        tileSheet = new Tile[w][h];
        init();
    }

    public void init() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                //invertimos los ejes por j(filas) i (columnas) para que respete el diseño de la matriz
                if (mapa[j][i] == 0) {
                    tileSheet[i][j] = new Tile(Color.green, false); // Casillas libres
                } else if (mapa[j][i] == 1) {
                    tileSheet[i][j] = new Tile(Color.red, true); // Obstáculos
                }
            }
        }
    }



    public Tile[][] getTileSheet() {
        return tileSheet;
    }

    public void setTileSheet(Tile[][] tileSheet) {
        this.tileSheet = tileSheet;
    }

}
