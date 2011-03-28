// @author: Philipp Jean-Jacques

package core;

import core.events.EventHandler;
import core.interfaces.Drawable;
import java.awt.Point;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.*;

public class GameCore implements Runnable {

    // game state constants
    public static final int RUN = 0;
    public static final int STOP = 1;
    public static final int PAUSE = 2;
    public static final int RESTART = 3;

    public static GameCore gameCore = null;

    private Thread thread = new Thread(this);
    private int state = 0;

    private Actor[] actors;

    private EventHandler eventHandler = new EventHandler();

    // physics
    private World world;

    public GameCore(){
        initialize();
        thread.start();
        gameCore = this;
    }

    public void initialize(){
        // TODO: add initialization code here
        world = new World(new Vector2f(0, 2000), 0);
    }

    public World getWorld(){
        return world;
    }

    public void setGravity(float x, float y){
        world.setGravity(x, y);
    }

    public void setState(int state){
        this.state = state;
    }

    public int getState(){
        return state;
    }

    public void run() {

        while(state != STOP && state != RESTART){
            if(state != PAUSE && state == RUN){

                eventHandler.handleEvents();
                world.step();   // physics
                
                // try rendering
                try{
                    // TODO: rendering
                    for(int i = 0; i < actors.length; i++){
                        if(actors[i] instanceof Drawable == true){
                            ((Drawable)actors[i]).draw(null);   // TODO: Draw to Volatile/Buffered Image
                        }
                    }
                }
                catch(Exception ex){
                }

            }

            // try sleeping
            try{
                // TODO: limit fps
                thread.sleep(7l);
            }
            catch(Exception ex){
            }
        }

        if(state == RESTART){
            gameCore = new GameCore();
        }

    }

}
