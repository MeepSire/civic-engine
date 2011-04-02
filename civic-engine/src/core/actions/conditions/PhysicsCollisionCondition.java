// @author: Philipp Jean-Jacques

package core.actions.conditions;

import core.Actor;
import core.actions.events.*;
import core.actors.PhysicsActor;

public class PhysicsCollisionCondition extends Condition {

    private PhysicsActor actor1, actor2;
    private boolean bool;

    public PhysicsCollisionCondition(PhysicsActor actor1, PhysicsActor actor2, boolean bool){
        this.actor1 = actor1;
        this.actor2 = actor2;
        this.bool = bool;
    }

    @Override
    public boolean check(Event evt){
        PhysicsCollisionEvent colEvt = (PhysicsCollisionEvent)evt;
        if(colEvt.evt.getBodyA().equals(actor1.getBody()) && colEvt.evt.getBodyB().equals(actor2.getBody())){
            return bool;
        }
        return !bool;
    }

    @Override
    public String toString(){
        return "Phyiscs: Collision Condition (" + actor1.toString() + ", " + actor2.toString() + ")";
    }

}