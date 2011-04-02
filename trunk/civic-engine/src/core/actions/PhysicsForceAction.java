/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core.actions;

import core.Actor;
import core.actors.*;
import net.phys2d.math.Vector2f;

// @author: Philipp Jean-Jacques

public class PhysicsForceAction extends Action {

    private Vector2f force;
    private PhysicsActor actor;

    public PhysicsForceAction(PhysicsActor actor, Vector2f force){
        this.actor = actor;
        this.force = force;
    }

    @Override
    public void execute(){
        actor.getBody().addForce(force);
    }

    @Override
    public String toString(){
        return "Physics Action: Add Force (" + actor.toString() + ", " + force.toString() + ")";
    }

}
