// @author: Philipp Jean-Jacques

package core.sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Animation implements Runnable {

    private BufferedImage[] frame;
    private BufferedImage activeFrame;
    private long speed;
    private Thread thread;
    private boolean rotation = true;
    private boolean pause = false;
    private String name;

    public Animation (String name, Sprite sprite, int startx, int starty, int endx, int endy, int speed, boolean rotation) {

        this.name = name;
        setSpeed(speed);
        this.rotation = rotation;

        Dimension frameSize = sprite.getFrameSize();

        int n = (Math.abs(startx - endx) * Math.abs(starty - endy)) / (frameSize.width*frameSize.height);

        frame = new BufferedImage[n];
        activeFrame = frame[0];

        BufferedImage spriteSheet = sprite.getSpriteSheet();

        int srcx = startx;
        int srcy = starty;

        for(int i = 0; i < n; i++){
            frame[i] = new SpriteFrame(spriteSheet, new Rectangle(srcx, srcy, frameSize.width, frameSize.height)).getImage();
            srcx += frameSize.width;
            if(srcx > endx){
                srcx = startx;
                srcy += frameSize.height;
                if(srcy > endy){
                    break;
                }
            }
        }

    }

    public String getName(){
        return name;
    }

    public void setSpeed(int speed){
        if(speed < 255){
            this.speed = (255-speed);
        } else this.speed = 0;
    }

    public void setAnimationRotation(boolean state){
        rotation = state;
    }

        public BufferedImage getActiveFrame(){
        return activeFrame;
    }

    public void pause(){    // TOGGLE
        pause = !pause;
    }

    public void reset(){
        activeFrame = frame[0];
    }

    public void stop(){
        reset();
        finished = true;
    }

    public void start(){

        if(!thread.isAlive()){
            stop();
            thread = new Thread(this);
            thread.start();
            finished = false;
            pause = false;
        }else{
            reset();
        }

    }

    public BufferedImage getNextFrame(){
        for(int i = 0; i < frame.length; i++){
            if(activeFrame.equals(frame[i])){
                if(i+1 < frame.length){
                    activeFrame = frame[i+1];
                }
            else {
                if(rotation = true);
                    activeFrame = frame[0];
                }
            break;
            }
        }
        return activeFrame;
    }

    // THREAD UPDATES THE FRAME
    private boolean finished = false;

    public void run() {

        while(finished == false){

            try {
                thread.sleep(speed);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sprite.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(!pause)getNextFrame();

        }

    }

    // FOR EASY IMAGE CREATION
    private class SpriteFrame {

        private BufferedImage image = null;

        public SpriteFrame(BufferedImage spriteSheet, Rectangle sourceRect){
            image = new BufferedImage(sourceRect.width, sourceRect.height, BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(spriteSheet, 0, 0, image.getWidth(), image.getHeight(), sourceRect.x, sourceRect.y, sourceRect.x + sourceRect.width, sourceRect.y + sourceRect.height, null);
        }

        public BufferedImage getImage(){
            return image;
        }

    }

}
