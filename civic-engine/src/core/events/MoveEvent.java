// @author: Philipp Jean-Jacques

package core.events;

import core.interfaces.*;
import java.awt.Point;

public class MoveEvent extends Event {

    Point destination;
    Moveable moveable;

    public MoveEvent(Point destination, Moveable moveable){
        this.moveable = moveable;
        this.destination = destination;
    }

    @Override
    public void execute(){
        moveable.setPosition(destination);
    }

}
