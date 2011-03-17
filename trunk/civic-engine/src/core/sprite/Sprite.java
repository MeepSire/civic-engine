package core.sprite;

import core.interfaces.Drawable;
import java.awt.*;
import java.awt.image.*;

public class Sprite implements Drawable {

    public BufferedImage spriteSheet;
    public Rectangle drawArea;
    public Dimension frameSize;

    public Sprite(Image spriteSheet, Dimension frameSize, Rectangle drawArea){
        
    }

    public void draw(Graphics g2d) {

    }

}
