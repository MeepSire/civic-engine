// @author: Philipp Jean-Jacques

package core;

import core.actions.ActionHandler;
import core.graphics.GameGraphics;
import core.interfaces.Drawable;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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

    public static ActionHandler actionHandler = new ActionHandler();

    private World world;

    private BufferedImage frame = null;

    private GameGraphics gfx;

    // TODO: create network-game protocol
    // TODO: add TCPHost to GameCore to enable Network gaming

    public GameCore(GameGraphics gfx, World world){
        initialize();
        thread.start();
        gameCore = this;
        setGraphics(gfx);
        setWorld(world);
    }


    public void initialize(){
        // TODO: add initialization code here
    }

    public void setGraphics(GameGraphics gfx){
        this.gfx = gfx;
    }

    public World getWorld(){
        return world;
    }

    public void setWorld(World world){
        this.world = world;
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

                // TODO: recieve Action Queue from host if in Client mode.

                actionHandler.executeActions();

                // TODO: send Action Queue to Clients if in Host mode.

                world.step();   // physics
                
                // try rendering
                try{

                    ArrayList list = new ArrayList();

                    for(int i = 0; i < actors.length; i++){
                        if(actors[i] instanceof Drawable == true) list.add(actors[i]);
                    }

                    frame = gfx.render((Drawable[])list.toArray());
                    
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
            gameCore = new GameCore(gfx, world);
        }

    }

}