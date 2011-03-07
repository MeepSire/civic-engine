// @author: Philipp Jean-Jacques

package core;

import core.events.*;
import core.interfaces.*;
import java.awt.Point;

public class Actor implements Moveable, Collideable, Actable{

    private Point position;

    public Actor(Point position) {
        this.position = position;
    }
    
    public void setPosition(Point destination){
        if(checkCollision() == false) position = destination;
    }

    public boolean checkCollision() {
        // TODO: check for collision
        return false;
    }

    public void act(Key key) {
        if(key.equals(Key.LEFT)) {
            EventHandler.addToEventQueue(new MoveEvent(new Point(position.x - 1, position.y), this));
            System.out.println("added event to event queue");
        }
        if(key.equals(Key.RIGHT)) {
            EventHandler.addToEventQueue(new MoveEvent(new Point(position.x + 1, position.y), this));
            System.out.println("added event to event queue");
        }

        // ...
        // just continue creating Events for the specific keys.

    }

}
