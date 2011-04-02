/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core.actors;

import core.graphics.Sprite;
import net.phys2d.raw.Body;
import org.newdawn.slick.Input;


// @author: Philipp Jean-Jacques

public abstract class StaticPhysicsActor extends PhysicsActor {

    public StaticPhysicsActor(float x, float y, Sprite sprite, Body body){
        super(x, y, sprite, body);
        body.setMoveable(false);
    }
    
}
