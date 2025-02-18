package main.java.com.entidad;

import main.java.com.Mapa;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Edificio extends Entidad{

    Mapa mapa;

    public Edificio(String nombre,Mapa mapa,int x,int y){
        super(nombre,x,y,"img/builds.png");
        this.mapa=mapa;

//seteamos esa casilla como obstaculo
        mapa.getTileSheet()[getX()][getY()].setObstaculo(true);
    }

    public void paint(Graphics g){

        g.drawImage(getTileImage(0),getX()*size,getY()*size,null);
    }

    public void resetCasilla(){
        boolean currentState= mapa.getTileSheet()[5][2].isObstaculo();
        mapa.getTileSheet()[5][2].setObstaculo(!currentState);
    }




}
