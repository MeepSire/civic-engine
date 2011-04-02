/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core.actors;

import org.newdawn.slick.Input;
import core.exceptions.NoSuchAnimationException;
import core.graphics.Animation;
import core.graphics.Sprite;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.shapes.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

// @author: Philipp Jean-Jacques

public class EmptyItemBox extends PhysicsActor {

    public EmptyItemBox(float x, float y) throws SlickException {
        
        super(x, y, new Sprite(new SpriteSheet(new Image("images/box.png"), 16, 16)), new Body("Box", new Box(16, 16), (float) 20.0));

        getSprite().addAnimation(new Animation("stand", getSprite(), 1, 0, 1, 0, 254, false));
        getSprite().setAnimation("stand");

        body.setRotDamping((float) 0.0);
        body.setRotatable(true);

    }

    public void act(Input input) {
        //
    }

}
