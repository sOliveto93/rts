package main.java.com;

import java.awt.*;

public class Mapa {
    TileSheet tileSheet;

    public Mapa(TileSheet ts) {
        this.tileSheet = ts;
    }

    public void paint(Graphics g) {
        Tile[][] tiles = getTileSheet();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {

                int tileSize = tiles[i][j].getSize();
                g.drawImage(tiles[i][j].getImage(),i*tileSize,j*tileSize,null);

            }
        }
    }

    public Tile[][] getTileSheet() {
        return tileSheet.getTileSheet();
    }

    public void setTileSheet(TileSheet tileSheet) {
        this.tileSheet = tileSheet;
    }
}
