// @author: Philipp Jean-Jacques

package core.actions.events;

import net.phys2d.raw.Body;
import net.phys2d.raw.CollisionEvent;

public class PhysicsCollisionEvent extends Event {

    public CollisionEvent evt;
    private Body body1,body2;

    public PhysicsCollisionEvent(CollisionEvent evt){
        this.evt = evt;
        if(evt!=null)body1=evt.getBodyA();
        if(evt!=null)body2=evt.getBodyB();
    }

    public Body getBodyA(){
        return body1;
    }
    
    public Body getBodyB(){
        return body2;
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