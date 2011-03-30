// @author: Philipp Jean-Jacques

package core.actions.conditions;

import core.Actor;
import core.actions.events.*;

public class PhysicsCollisionCondition extends Condition {

    private static final String name = "Phyiscs: Collision Condition";

    private Actor actor1, actor2;
    private boolean bool;

    public PhysicsCollisionCondition(Actor actor1, Actor actor2, boolean bool){
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

}