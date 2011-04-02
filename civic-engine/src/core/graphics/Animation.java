// @author: Philipp Jean-Jacques

package core.graphics;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;

public class Animation implements Runnable {

    private Image[] frame;
    private Image activeFrame;
    private long speed;
    private Thread thread;
    private boolean rotation = true;
    private boolean pause = false;
    private String name;

    public Animation (String name, Sprite sprite, int startx, int starty, int endx, int endy, int speed, boolean rotation) {

        this.name = name;
        setSpeed(speed);
        this.rotation = rotation;

        ArrayList list = new ArrayList();

        for(int x = startx; x <= endx; x++){
            for(int y = starty; y <= endy; y++){
                list.add(sprite.getSpriteSheet().getSprite(x, y));
            }
        }

        frame = new Image[list.size()];
        
        for(int i = 0; i < frame.length; i++){
            frame[i] = (Image)list.get(i);
        }
        
        if(list.size() > 0)activeFrame = frame[0];

        thread = new Thread(this);
        
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

    public Image getActiveFrame(){
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
            thread = new Thread(this);
            stop();
            thread.start();
            finished = false;
            pause = false;
        }else{
            reset();
        }

    }

    public Image getNextFrame(){
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

}
