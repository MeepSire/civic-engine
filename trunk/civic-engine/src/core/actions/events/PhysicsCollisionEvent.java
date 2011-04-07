// @author: Philipp Jean-Jacques

package core.actions.events;

import net.phys2d.raw.CollisionEvent;

public class PhysicsCollisionEvent extends Event {

    public CollisionEvent evt;

    public PhysicsCollisionEvent(CollisionEvent evt){
        this.evt = evt;
    }

    @Override
    public void trigger(){
        for(int i = 0; i < listeners.size(); i++){
            EventListener listener = (EventListener)listeners.get(i);
            listener.eventTriggered(this);
        }
    }

    @Override
    public String toString(){
        return "Physics Event: Collision (" + evt.getBodyA().toString().substring(7 ,evt.getBodyA().toString().lastIndexOf(39)) + " [" + evt.getBodyA().getID() + "]" + ", " + evt.getBodyB().toString().substring(7 ,evt.getBodyB().toString().lastIndexOf(39)) + " [" + evt.getBodyB().getID() + "])";
    }

}