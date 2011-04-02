// @author: Philipp Jean-Jacques

package core.graphics;

import core.exceptions.NoSuchAnimationException;
import java.util.ArrayList;
import java.util.Arrays;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Sprite {

    private final SpriteSheet spriteSheet;
    private ArrayList animation = new ArrayList();
    private Animation activeAnimation;
    private boolean flipH, flipV = false;

    public Sprite(SpriteSheet spriteSheet){

        this.spriteSheet = spriteSheet;
        addAnimation(new Animation("NONE", this, 0, 0, 0, 0, 0, false));

    }

    public void flip(boolean horizontal, boolean vertical){
        flipH = horizontal;
        flipV = vertical;
    }

    public void addAnimation(Animation ani){
        animation.add(ani);
    }

    public void removeAnimation(Animation ani){
        animation.remove(ani);
    }

    public void setAnimation(String name) {
        Animation ani = getAnimationForName(name);
        if(ani != null){
            if(activeAnimation != null)activeAnimation.stop(); // STOP THE OLD ANIMATION
            activeAnimation = ani;
            activeAnimation.start(); // START THE NEW ANIMATION
        }
        else{
            System.out.println("No Such Animation");
        }
    }

    public String[] getAnimationNames(){
        ArrayList names = new ArrayList();

        for(int i = 0; i < animation.size(); i++){
            names.add(((Animation)animation.get(i)).getName());
        }

        return (String[])names.toArray();
    }

    public Animation[] getAnimations(){
        Animation[] animations = new Animation[animation.size()];
        for(int i = 0; i < animations.length; i++){
            animations[i] = (Animation)animation.get(i);
        }
        return animations;
    }

    private Animation getAnimationForName(String name){
        Animation[] animations = getAnimations();
        for(int i = 0; i < animations.length; i++){
            if(animations[i].getName().equals(name)){
                return animations[i];
            }
        }
        return null;
    }

    public SpriteSheet getSpriteSheet(){
        return spriteSheet;
    }

    public Image getActiveFrame(){
        return activeAnimation.getActiveFrame().getFlippedCopy(flipH, flipV);
        //return spriteSheet.getSprite(0, 0);
    }

    // MIGHT BE USED FOR PER-PIXEL COLLISION (SLOW)
    /*
    public Polygon getBounds(Color alphaColor){

        BufferedImage image = (BufferedImage)getActiveFrame();
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
    */

}