package main.java.com;

import java.awt.*;

public class Mapa {
    TileSheet tileSheet;
    int tileSize=64;
    public Mapa(TileSheet ts) {
        this.tileSheet = ts;
    }

    public void paint(Graphics g, Camara camara) {

        int screenWidth = g.getClipBounds().width;  // Ancho de la ventana
        int screenHeight = g.getClipBounds().height; // Alto de la ventana

        // Calcular los límites visibles en función de la cámara
        int startX = Math.max(0, camara.getX() / tileSize);
        int startY = Math.max(0, camara.getY() / tileSize);
        int endX = Math.min(tileSheet.getTileSheet().length, (camara.getX() + screenWidth) / tileSize + 1);
        int endY = Math.min(tileSheet.getTileSheet()[0].length, (camara.getY() + screenHeight) / tileSize + 1);
        // Desplazar el sistema de coordenadas para dibujar correctamente según la cámara
        g.translate(-camara.getX(), -camara.getY());

        // Dibujar solo los tiles visibles
        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                Tile tile = tileSheet.getTileSheet()[i][j];

                g.drawImage(tile.getImage(), i * tileSize, j * tileSize, null); // Dibujar el tile
                g.drawRect(i*tileSize,j*tileSize,tileSize,tileSize);
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
