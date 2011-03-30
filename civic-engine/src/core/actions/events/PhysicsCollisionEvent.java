// @author: Philipp Jean-Jacques

package core.actions.events;

import net.phys2d.raw.CollisionEvent;

public class PhysicsCollisionEvent extends Event{

    public CollisionEvent evt;

    public PhysicsCollisionEvent(CollisionEvent evt){
        this.evt = evt;
    }

    @Override
    public void trigger(){
        for(int i = 0; i < listeners.length; i++){
            listeners[i].eventTriggered(this);
        }
    }

    @Override
    public String toString(){
        return "Physics Event: Collision (" + evt.getBodyA() + ", " + evt.getBodyB() + ")";
    }

}