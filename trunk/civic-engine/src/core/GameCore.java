// @author: Philipp Jean-Jacques

package core;

import core.events.EventHandler;
import java.awt.Point;

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

    public GameCore(){
        initialize();
        thread.start();
        gameCore = this;
    }

    public void initialize(){
        // TODO: add initialization code here
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

                EventHandler.handleEvents();

                // try rendering
                try{
                    // TODO: rendering
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
