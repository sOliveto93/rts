package main;

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
                g.setColor(tiles[i][j].getBackground());
                g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);

                g.setColor(Color.black);
                g.drawRect(i * tileSize, j * tileSize, tileSize, tileSize);

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
