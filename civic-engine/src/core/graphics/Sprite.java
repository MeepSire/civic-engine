// @author: Philipp Jean-Jacques

package core.graphics;

import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Sprite {

    private final SpriteSheet spriteSheet;
    private ArrayList animation = new ArrayList();
    private Animation activeAnimation;
    private boolean flipH, flipV = false;

    public Sprite(SpriteSheet spriteSheet){

        this.spriteSheet = spriteSheet;

    }

    public void flip(boolean horizontal, boolean vertical){
        flipH = horizontal;
        flipV = vertical;
    }

    public void setAnimation(Animation ani) {
        if(ani != null){
            activeAnimation = ani;
            if(!activeAnimation.equals(ani)){
                activeAnimation.start();                            // START THE NEW ANIMATION
            }
        }
    }

    public Animation getCurrentAnimation(){
        return activeAnimation;
    }

    public SpriteSheet getSpriteSheet(){
        return spriteSheet;
    }

    public Image getCurrentFrame(){
        if(activeAnimation != null){
            activeAnimation.draw(-20000, -20000);
            return activeAnimation.getCurrentFrame().getFlippedCopy(flipH, flipV);
        }
        else {
            return null;
        }
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