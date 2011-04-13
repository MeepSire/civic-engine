/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core.actors;

import core.Actor;
import core.GameCore;
import core.Trigger;
import core.actions.Action;
import core.actions.conditions.PhysicsCollisionCondition;
import core.actions.events.PhysicsCollisionEvent;
import core.graphics.Sprite;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.*;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

// @author: Philipp Jean-Jacques

public class Fire extends PhysicsActor {

    protected Sprite sprite = new Sprite(new SpriteSheet(new Image("images/fire.png"), 16, 16));

    public Animation STAND = new Animation (sprite.getSpriteSheet(), 0, 0, 0, 0, false, 1, false);

    Trigger myTrigger = new Trigger();

    public Fire(float x, float y) throws SlickException {

        super(x, y, new Sprite(new SpriteSheet(new Image("images/fire.png"), 16, 16)), new Body("Fire", new Circle(8), (float) 10.0));

        myTrigger.addEvent(new PhysicsCollisionEvent(null));
        myTrigger.addCondition(new PhysicsCollisionCondition(this,true));
        myTrigger.addAction(new CollAction());

        getSprite().setAnimation(STAND);

        body.setRotDamping((float) -1);
        body.setRotatable(true);
        body.setTorque(200);

    }

    public void act(Input input) {
        // CANNOT ACT
    }

    private class CollAction extends Action{
        @Override
        public void execute(){
            /*PhysicsCollisionEvent pce = (PhysicsCollisionEvent)myTrigger.getTriggeredEvent();
            Actor[] actors = GameCore.gamecore.getActors();
            for(int i = 0; i < actors.length; i++){
                if(actors[i] instanceof PhysicsActor){
                    PhysicsActor physicsActor = (PhysicsActor) actors[i];
                    if(physicsActor.bodyid == pce.getBodyA().getID()){
                        GameCore.gamecore.removeActor(physicsActor);
                    }
                    if(physicsActor.bodyid == pce.getBodyB().getID()){
                        GameCore.gamecore.removeActor(physicsActor);
                    }
                }
            }
             * 
             */
        }
    }
}