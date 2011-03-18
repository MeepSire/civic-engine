// @author: Philipp Jean-Jacques

package core.actors;

import core.*;
import core.events.*;
import core.interfaces.*;
import java.awt.Point;

public class TestActor extends Actor implements Moveable {

    private Point position;
    private String name;

    public TestActor(Point position, String name){
        this.position = position;
        this.name = name;
    }

    @Override
    public void act(Key key){

        Key[] keys = this.getKeys();
/*
        if(key.getKeyCode() == keys[0].getKeyCode()){
            EventHandler.addToEventQueue(new MoveEvent(new Point(position.x - 1, position.y), this));
        }
        if(key.getKeyCode() == keys[1].getKeyCode()){
            EventHandler.addToEventQueue(new MoveEvent(new Point(position.x + 1, position.y), this));
        }
        if(key.getKeyCode() == keys[2].getKeyCode()){
            EventHandler.addToEventQueue(new MoveEvent(new Point(position.x, position.y - 1), this));
        }
        if(key.getKeyCode() == keys[3].getKeyCode()){
            EventHandler.addToEventQueue(new MoveEvent(new Point(position.x, position.y + 1), this));
        }

        // print to console if key pressed:
        for(int i = 0; i < keys.length; i++){
            if(key.getKeyCode() == keys[i].getKeyCode()){
                EventHandler.addToEventQueue(new ConsoleEvent("Position: " + position.x + ", " + position.y));
                //EventHandler.addToEventQueue(new SpeedTestEvent());
            }
        }
*/
    }

    public void setPosition(Point destination) {
        this.position = destination;
    }

    public Point getPosition() {
        return position;
    }

}
