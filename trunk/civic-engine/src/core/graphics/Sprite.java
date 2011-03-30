// @author: Philipp Jean-Jacques

package core.graphics;

import core.exceptions.NoSuchAnimationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {

    private Color alphaColor = new Color(0,0,0,0);
    private Dimension frameSize;
    private final BufferedImage spriteSheet;
    private Animation[] animation = new Animation[0];
    private Animation activeAnimation;

    public Sprite(BufferedImage spriteSheet, Dimension frameSize, Color alphaColor){

        this.alphaColor = alphaColor;
        this.frameSize = frameSize;
        this.spriteSheet = spriteSheet;

        addAnimation(new Animation("NONE", this, 0, 0, frameSize.width, frameSize.height, 0, false));

    }

    public void addAnimation(Animation ani){
        ArrayList anis = new ArrayList();
        for(int i = 0; i < animation.length; i++){
            anis.add(animation[i]);
        }
        anis.add(ani);
        animation = (Animation[])anis.toArray();
    }

    public void removeAnimation(Animation ani){
        ArrayList anis = new ArrayList();
        for(int i = 0; i < animation.length; i++){
            anis.add(animation[i]);
        }
        anis.remove(ani);
        animation = (Animation[])anis.toArray();
    }

    public void setAnimation(String name) throws NoSuchAnimationException {
        Animation ani = getAnimationForName(name);
        if(ani != null){
            activeAnimation.stop(); // STOP THE OLD ANIMATION
            activeAnimation = ani;
            activeAnimation.start(); // START THE NEW ANIMATION
        }
        else{
            new NoSuchAnimationException(name + " in " + this.toString());
        }
    }

    public String[] getAnimationNames(){
        ArrayList names = new ArrayList();

        for(int i = 0; i < animation.length; i++){
            names.add(animation[i].getName());
        }

        return (String[])names.toArray();
    }

    public Animation[] getAnimations(){
        return animation;
    }

    private Animation getAnimationForName(String name){
        for(int i = 0; i < animation.length; i++){
            if(animation[i].getName().equals(name)){
                return animation[i];
            }
        }
        return null;
    }

    public Dimension getFrameSize(){
        return frameSize;
    }

    public BufferedImage getSpriteSheet(){
        return spriteSheet;
    }

    public BufferedImage getActiveFrame(){
        return activeAnimation.getActiveFrame();
    }

    // MIGHT BE USED FOR PER-PIXEL COLLISION (SLOW)
    public Polygon getBounds(){

        BufferedImage image = getActiveFrame();
        int width = image.getWidth();
        int height = image.getHeight();

        boolean[][] visible = new boolean[width][height];

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                int c = image.getRGB(x,y);
                int alpha = (c & 0xff000000) >> 24;
                int red = (c & 0x00ff0000) >> 16;
                int green = (c & 0x0000ff00) >> 8;
                int blue = (c & 0x000000ff);
                Color color = new Color(red,green,blue,alpha);
                if(color.equals(alphaColor) || color.getAlpha() < 0.5)visible[x][y] = false;
                else visible[x][y] = true;
            }
        }

        Polygon shape = new Polygon();

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if(visible[x][y] == true && (visible[x-1][y] == false || visible[x+1][y] == false || visible[x][y-1] == false ||  visible[x][y+1] == false)){
                    shape.addPoint(x, y);
                }
            }
        }

        return shape;

    }

    public void setAlphaColor(Color c){
        alphaColor = c;
    }

}