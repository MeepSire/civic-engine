// @author: Philipp Jean-Jacques

package core.graphics;

import core.graphics.filter.GraphicsFilter;
import core.interfaces.Drawable;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameGraphics {

    Dimension resolution;
    GraphicsFilter[] filter = new GraphicsFilter[0];

    public GameGraphics(int width, int height){
        resolution = new Dimension(width, height);
    }

    public BufferedImage getEmptyFrame(){
        return new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_BYTE_BINARY);
    }
    
    public void addFilter(GraphicsFilter filter){
        ArrayList list = new ArrayList();
        for(int i = 0; i < this.filter.length; i++){
            list.add(this.filter[i]);
        }
        list.add(filter);
        this.filter = (GraphicsFilter[])list.toArray();
    }

    private BufferedImage applyFilter(BufferedImage img){
        for(int i = 0; i < filter.length; i++){
            img = filter[i].apply(img);
        }
        return img;
    }

    public BufferedImage render(Drawable[] drawable){

        // CREATE
        BufferedImage image = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = image.createGraphics();
        g2d.setComposite(AlphaComposite.Src);

        // CLEAR
        g2d.setColor(new Color(0,0,0,0));
        g2d.fillRect(0, 0, resolution.width, resolution.height);
        g2d.setBackground(new Color(0,0,0,0));

        // DRAW
        for(int i = 0; i < drawable.length; i++){
            drawable[i].draw(g2d);
        }

        // FILTER
        image = applyFilter(image);
        
        return image;

    }

}