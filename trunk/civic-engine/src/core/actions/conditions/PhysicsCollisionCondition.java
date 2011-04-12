// @author: Philipp Jean-Jacques

package core.actions.conditions;

import core.Actor;
import core.actions.events.*;
import core.actors.PhysicsActor;

public class PhysicsCollisionCondition extends Condition {

    private PhysicsActor actor1 = null, actor2 = null;
    private boolean bool;

    public PhysicsCollisionCondition(PhysicsActor actor1, PhysicsActor actor2, boolean bool){
        this.actor1 = actor1;
        this.actor2 = actor2;
        this.bool = bool;
    }

    public PhysicsCollisionCondition(PhysicsActor actor1, boolean bool){
        this.actor1 = actor1;
        this.actor2 = actor2;
        this.bool = bool;
    }

    public PhysicsActor[] getActors(){
        return new PhysicsActor[]{actor1, actor2};
    }

    @Override
    public boolean check(Event evt){
        PhysicsCollisionEvent colEvt = (PhysicsCollisionEvent)evt;
        if(actor2 == null){
            if(colEvt.evt.getBodyA().equals(actor1.getBody()) || colEvt.evt.getBodyB().equals(actor1.getBody())){
                return bool;
            }
        }
        else{
            if(colEvt.evt.getBodyA().equals(actor1.getBody()) && colEvt.evt.getBodyB().equals(actor2.getBody()) || colEvt.evt.getBodyA().equals(actor2.getBody()) && colEvt.evt.getBodyB().equals(actor1.getBody())){
                return bool;
            }
        }
        return !bool;
    }

    @Override
    public String toString(){
        if(actor2 != null){
            return "Phyiscs: Collision Condition (" + actor1.getBody().toString().substring(7 , actor1.getBody().toString().lastIndexOf(39)) + " [" + actor1.getBody().getID() + "]" + ", " + actor2.getBody().toString().substring(7 ,actor2.getBody().toString().lastIndexOf(39)) + " [" + actor2.getBody().getID() + "]" + ")";
        }
        else{
            return "Phyiscs: Collision Condition (" + actor1.getBody().toString().substring(7 , actor1.getBody().toString().lastIndexOf(39)) + " [" + actor1.getBody().getID() + "]" + ")";
        }
    }

}